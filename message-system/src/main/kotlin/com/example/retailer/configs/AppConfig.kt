package com.example.retailer.configs

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate

@Configuration
class AppConfig {

    @Bean
    fun queue(): Queue {
        return Queue("retailer_queue")
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun bindingRetailer(exchange: TopicExchange, queue: Queue) : Binding {
        return BindingBuilder.bind(queue).to(exchange).with("retailer.SirKot.#")
    }

    @Bean
    fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange("distributor_exchange")
        return rabbitTemplate
    }

}