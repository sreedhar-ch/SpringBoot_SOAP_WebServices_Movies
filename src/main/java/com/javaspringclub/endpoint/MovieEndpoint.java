package com.javaspringclub.endpoint;

import com.javaspringclub.entity.MovieEntity;
import com.javaspringclub.exception.ServiceFaultException;
import com.javaspringclub.gs_ws.*;
import com.javaspringclub.service.MovieEntityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.soap.SOAPException;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class MovieEndpoint {

    public static final String NAMESPACE_URI = "http://www.javaspringclub.com/movies-ws";

    private MovieEntityService service;

    public MovieEndpoint() {

    }

    @Autowired
    public MovieEndpoint(MovieEntityService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMovieByIdRequest")
    @ResponsePayload
    public GetMovieByIdResponse getMovieById(@RequestPayload GetMovieByIdRequest request) throws SOAPException {
        GetMovieByIdResponse response = new GetMovieByIdResponse();
        try {
            MovieEntity movieEntity = service.getEntityById(request.getMovieId());
            MovieType movieType = new MovieType();
            BeanUtils.copyProperties(movieEntity, movieType);

			//movieType.setMovieId(movieEntity.getMovieId());
			/*movieType.setTitle(movieEntity.getTitle());
			movieType.setCategory(movieEntity.getCategory());
			movieType.setSpaceship(movieEntity.getSpaceship());
            */
            response.setMovieType(movieType);
        } catch (Exception ex) {
            String faultString = "SOP-330011 Error while executing the method 'GetOfferContentById' of service 'nms:offer'.";
            String details = "[nms_offer_GetOfferContentById] Offer with offer id = 1675064 doesn't exist.";
            throw new ServiceFaultException(faultString, details);

			/*ServiceStatus ss = new ServiceStatus();
			ss.setStatusCode("STC");
			ss.setMessage("This is message");
			ss.setDetail("This is detail");*/

			/*SOAPFactory f = SOAPFactory.newInstance();
			SOAPFault soapFault = f.createFault();
			soapFault.setFaultString("Test Fault String ****");

			Detail detail = soapFault.addDetail();
			detail = soapFault.getDetail();
			detail.setNodeValue("THIS IS DETAILS NODE VALUE");
			QName qName = new QName("http://www.Hello.org/greeter", "TestFault", "ns");
			DetailEntry de = detail.addDetailEntry(qName);
			qName = new QName("http://www.Hello.org/greeter", "ErrorCode", "ns");
			SOAPElement errorElement = de.addChildElement(qName);
			errorElement.setTextContent("errorcode");
			throw new SOAPFaultException(soapFault);*/
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllMoviesRequest")
    @ResponsePayload
    public GetAllMoviesResponse getAllMovies(@RequestPayload GetAllMoviesRequest request) {
        GetAllMoviesResponse response = new GetAllMoviesResponse();
        List<MovieType> movieTypeList = new ArrayList<MovieType>();
        List<MovieEntity> movieEntityList = service.getAllEntities();
        for (MovieEntity entity : movieEntityList) {
            MovieType movieType = new MovieType();
            BeanUtils.copyProperties(entity, movieType);
            movieTypeList.add(movieType);
        }
        response.getMovieType().addAll(movieTypeList);

        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addMovieRequest")
    @ResponsePayload
    public AddMovieResponse addMovie(@RequestPayload AddMovieRequest request) {
        AddMovieResponse response = new AddMovieResponse();
        MovieType newMovieType = new MovieType();
        ServiceStatus serviceStatus = new ServiceStatus();

        MovieEntity newMovieEntity = new MovieEntity(request.getTitle(), request.getCategory());
        MovieEntity savedMovieEntity = service.addEntity(newMovieEntity);

        if (savedMovieEntity == null) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity");
        } else {

            BeanUtils.copyProperties(savedMovieEntity, newMovieType);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
        }

        response.setMovieType(newMovieType);
        response.setServiceStatus(serviceStatus);
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateMovieRequest")
    @ResponsePayload
    public UpdateMovieResponse updateMovie(@RequestPayload UpdateMovieRequest request) {
        UpdateMovieResponse response = new UpdateMovieResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        // 1. Find if movie available
        MovieEntity movieFromDB = service.getEntityByTitle(request.getTitle());

        if (movieFromDB == null) {
            serviceStatus.setStatusCode("NOT FOUND");
            serviceStatus.setMessage("Movie = " + request.getTitle() + " not found");
        } else {

            // 2. Get updated movie information from the request
            movieFromDB.setTitle(request.getTitle());
            movieFromDB.setCategory(request.getCategory());
            // 3. update the movie in database

            boolean flag = service.updateEntity(movieFromDB);

            if (flag == false) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while updating Entity=" + request.getTitle());
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content updated Successfully");
            }


        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteMovieRequest")
    @ResponsePayload
    public DeleteMovieResponse deleteMovie(@RequestPayload DeleteMovieRequest request) {
        DeleteMovieResponse response = new DeleteMovieResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        boolean flag = service.deleteEntityById(request.getMovieId());

        if (flag == false) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Exception while deletint Entity id=" + request.getMovieId());
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }

        response.setServiceStatus(serviceStatus);
        return response;
    }

}
