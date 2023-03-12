package com.example.th1.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {

    public static <T> List<T> convertToList(T [] arrayObject) {
        return Arrays.asList(arrayObject);
    }
    public static <T> List<T> search(Class<T> clazz,List<T> data, String searchInput){
        List<T> result= data.stream().filter( s->{
            Method[] methods=clazz.getDeclaredMethods();
            for(Method m:methods){
                try {
                    if(m.getName().startsWith("set")||m.getName().startsWith("to")){
                        continue;
                    }
                    Method method= s.getClass().getMethod(m.getName());
                    Object o=method.invoke(s);
                    Class<?> returnType= method.getReturnType();
                    if(returnType.getName().equalsIgnoreCase(String.class.getName())){
                        String value=((String) o).toLowerCase();
                        if(value.contains(searchInput.toLowerCase()))
                            return true;
                    }
                    if(returnType.getName().equalsIgnoreCase(Integer.class.getName())){
                        Integer value=(Integer) o;
                        if(value == Integer.parseInt(searchInput))
                            return true;
                    }
                    if(returnType.getName().equalsIgnoreCase(Long.class.getName())){
                        Long value=(Long) o;
                        if(value == Long.parseLong(searchInput))
                            return true;
                    }

                }  catch (NoSuchMethodException e) {
                    e.printStackTrace();
                        return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                    return false;
                }
            };
            return  false;
        }).collect(Collectors.toList());
        return result;
    }
}

