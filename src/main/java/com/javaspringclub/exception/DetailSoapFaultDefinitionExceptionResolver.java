package com.javaspringclub.exception;

import javax.xml.namespace.QName;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.saaj.SaajSoapFaultException;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import com.javaspringclub.gs_ws.ServiceStatus;

import java.util.Locale;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

	private static final QName DETAIL = new QName("detail");

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		logger.warn("Exception processed ", ex);
		if (ex instanceof ServiceFaultException) {
			String details = ((ServiceFaultException) ex).getDetails();

			SoapFaultDetail soapFaultDetail = fault.addFaultDetail();
			soapFaultDetail.addFaultDetailElement(DETAIL).addText(details);
		}
	}
}


