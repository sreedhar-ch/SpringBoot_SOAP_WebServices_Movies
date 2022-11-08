package com.javaspringclub.exception;

import com.javaspringclub.gs_ws.ServiceStatus;

public class ServiceFaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ServiceStatus serviceStatus;

	private String details;

	public ServiceFaultException(String message, ServiceStatus serviceStatus) {
		super(message);
		this.serviceStatus = serviceStatus;
	}

	public ServiceFaultException(String message, Throwable e, ServiceStatus serviceStatus) {
		super(message, e);
		this.serviceStatus = serviceStatus;
	}

	public ServiceFaultException(String message, String details) {
		super(message);
		this.details = details;
	}

	public ServiceFaultException(String message, Throwable e, String details) {
		super(message, e);
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}

