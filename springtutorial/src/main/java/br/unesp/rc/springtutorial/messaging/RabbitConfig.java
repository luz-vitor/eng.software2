package br.unesp.rc.springtutorial.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "pessoa.events";
    public static final String QUEUE_AUDITORIA = "pessoa.auditoria.q";

    public static final String EXCHANGE_DLX = "pessoa.dlx";
    public static final String QUEUE_DLQ = "pessoa.auditoria.dlq";

    @Bean
    public TopicExchange pessoaExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(EXCHANGE_DLX, true, false);
    }

    @Bean
    public Queue auditoriaQueue() {
        return QueueBuilder
                .durable(QUEUE_AUDITORIA)
                .deadLetterExchange(EXCHANGE_DLX)
                .deadLetterRoutingKey(QUEUE_DLQ)
                .build();
    }

    @Bean
    public Queue auditoriaDeadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DLQ).build();
    }

    @Bean
    public Binding auditoriaBinding(Queue auditoriaQueue, TopicExchange pessoaExchange) {
        return BindingBuilder
                .bind(auditoriaQueue)
                .to(pessoaExchange)
                .with("pessoa.#");
    }

    @Bean
    public Binding deadLetterBinding(Queue auditoriaDeadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder
                .bind(auditoriaDeadLetterQueue)
                .to(deadLetterExchange)
                .with(QUEUE_DLQ);
    }

    /**
     * Sem isto o Spring AMQP serializa usando serializacao Java nativa, que
     * acopla produtor e consumidor a mesma classe. JSON mantem a mensagem
     * legivel no painel do RabbitMQ e independente de linguagem.
     *
     * O JavaTimeModule e necessario para o campo Instant do PessoaEvent.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);

        return template;
    }
}
