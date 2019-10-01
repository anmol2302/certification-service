package org.sunbird.service;


import org.sunbird.BaseException;
import org.sunbird.request.Request;
import org.sunbird.response.Response;

/**
 * this is an interface class for implementing certificate related operations
 *
 */
public interface ICertService{

    String add(Request request) throws BaseException;

    Response  validate(Request request) throws BaseException;


}
