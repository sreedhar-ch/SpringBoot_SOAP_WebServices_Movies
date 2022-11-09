package com.javaspringclub.config;

import com.javaspringclub.gs_ws.GetMovieByIdRequest;
import com.javaspringclub.gs_ws.GetMovieByIdResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Response;
import java.io.File;


public class SOAPConnector extends WebServiceGatewaySupport {
    public GetMovieByIdResponse getMovieById(String url, GetMovieByIdRequest request){
        Object res = getWebServiceTemplate().marshalSendAndReceive(url, request);
        GetMovieByIdResponse response = (GetMovieByIdResponse) res;
        response.getMovieType();
        return response;
    }

    private void main1() throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(new File("src/forum11465653/input.xml"));
        Node getNumberResponseElt = d.getElementsByTagNameNS("http://example.com/", "getNumberResponse").item(0);

        JAXBContext jc = JAXBContext.newInstance(Response.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<Response> je = unmarshaller.unmarshal(new DOMSource(getNumberResponseElt), Response.class);
        System.out.println(je.getName());
        System.out.println(je.getValue());
    }
}