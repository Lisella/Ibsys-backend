package de.Ibsys.ibsys;

import de.Ibsys.ibsys.entity.Input;
import jakarta.xml.bind.JAXBException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

@Configuration
public class JAXBConfig {

    @Bean
    public Marshaller XMLToJava() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Input.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }

    @Bean
    public Unmarshaller JavaToXML() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Input.class);
        return jaxbContext.createUnmarshaller();
    }
}
