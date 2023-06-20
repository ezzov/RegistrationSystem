package com.example.registrationsystem.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@Setter
@Getter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDto {

    @XmlElement(nillable = true)
    @XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
    Long id;

    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlElement(nillable = true)
    @XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
    Boolean isKiosk;

    @XmlElement(nillable = true)
    @XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
    AppointmentDto appointment = new AppointmentDto();

}
