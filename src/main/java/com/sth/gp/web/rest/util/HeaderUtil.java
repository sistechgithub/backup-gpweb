package com.sth.gp.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-gpApp-alert", message);
        headers.add("X-gpApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        //return createAlert("A new " + entityName + " is created with identifier " + param, param);
        return createAlert("Um(a) novo(a) " + entityName + " foi criado(a) com o identificador " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        //return createAlert("A " + entityName + " is updated with identifier " + param, param);
        return createAlert("O(A) " + entityName + " foi atualizado(a) com identificador " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        //return createAlert("A " + entityName + " is deleted with identifier " + param, param);
        return createAlert("Um(a) " + entityName + " foi deletado(a) com identificador " + param, param);
    }
}
