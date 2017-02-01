package com.epam.hostel.command.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by ASUS on 01.02.2017.
 */
public class CommandHelper {
    private final static Logger logger = Logger.getLogger(CommandHelper.class);


    public static int getInt(String innerString) throws IOException{
        if(innerString != null){
            try{
                int value = Integer.parseInt(innerString);
                return value;
            } catch (NumberFormatException e){
                logger.error("Wrong string for getting number");
                return -1;
            }
        } else {
            return -1;
        }
    }
}
