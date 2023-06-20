package com.example.registrationsystem.service;

import com.example.registrationsystem.dto.xml.AppointmentDto;
import com.example.registrationsystem.dto.xml.XmlDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class XmlService {

    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                .createContext(new Class[]{XmlDto.class, AppointmentDto.class}, null);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        XmlDto dto = new XmlDto();
        dto.setIsKiosk(true);
//        AppointmentDto appointmentDto = new AppointmentDto();
//        dto.setAppointment(appointmentDto);
        jaxbMarshaller.marshal(dto, System.out);
    }
}

