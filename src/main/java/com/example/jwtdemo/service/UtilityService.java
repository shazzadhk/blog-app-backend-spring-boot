package com.example.jwtdemo.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UtilityService {

    public Date formatDate(Date receiveDate){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(receiveDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateWithoutTime;
    }
}
