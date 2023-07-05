package org.br.mineradora.message;

import org.br.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){ //producer
        LOG.info("-- Enviando Cotação para Tópico Kafka --");
        quotationRequestEmitter.send(quotation).toCompletableFuture().join();
    }

    @Incoming("quotation")   //consummer
    public void receiveNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Recebimento de cotação para Tópico Kafka --");
        LOG.info(quotation.toString());
    }

}