package com.siwa.helpers;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

import com.siwa.annotations.StringOperation;
import com.siwa.exceptions.AlgorithmDuplicateName;
import com.siwa.interfaces.Operation;

/**
 * This class contain the String Operations map to get the posible operations on the strings.
 *
 */
public class StringOperationsMap {
    /**
     * This attribute save the instance of object in memory to rescue in every time of app execution.
     */
    private static StringOperationsMap instance = null;
    /**
     * The attributes set the package name and the hashtable with the class mapped.
     */
    private String operationPackage = "com.siwa.operations";
    private Hashtable<String, Operation> operationsMap;
    
    /**
     * The constructor of class. Its call's from root App. If needed in other place, 
     * please call across static getInstance method.
     */
    public StringOperationsMap() {
        this.operationsMap = new Hashtable<String, Operation>();
        this.mapOperations();
        // Save the instance in static class declaration
        instance = this;
    }
    
    /**
     * This method iterate the classes in the package set in operationPackage. 
     * Each class is verified about it's or not a Operation class.
     */
    private void mapOperations() {
          ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
          URL resource = classLoader.getResource(this.operationPackage.replace(".", "/"));
          File packageDir = new File(resource.getFile());
          File[] classes = packageDir.listFiles();
          for (File classFile : classes) {
              if (!classFile.isDirectory()) this.checkOperation(classFile);
          }
    }
    
    /**
     * This method identify if the provide class file is a Operation implementation.
     * If is a String Operation class, the method pass them to checkDeclaredOperation method.
     * @param {File} classFile Provide the class file that we want verify is a String Operation implementation.
     */
    private void checkOperation(File classFile) {
        try {
            Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(this.operationPackage + 
                  "." + classFile.getName().replaceAll(".class$", ""));
            if (Operation.class.isAssignableFrom(cls)) {
                this.checkDeclaredOperation(classFile, cls);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The operation premaped not in the package " + classFile.getName());
        }
    }
    
    /**
     * This method identify if the provide class file is defined like a Operation by annotation.
     * If is a Operation class, the method add to OperationsMap.
     * @param {File} classFile Provide the class file that we want verify is a String Operation class.
     * @param {Class<?>} cls Provide class definition that we want to inspect if have the Operation annotation.
     */
    private void checkDeclaredOperation(File classFile, Class<?> cls) {
        try {
            StringOperation operation = cls.getAnnotation(StringOperation.class);
            if (operation != null) {
                if (!this.operationsMap.containsKey(operation.name())) {
                    this.operationsMap.put(operation.name(), (Operation) cls.newInstance());
                } else {
                    throw new AlgorithmDuplicateName("The algorithm " + operation.name() + " in " +
                          cls.getName() + "class have a duplicated named");
                }
            }
        } catch (AlgorithmDuplicateName e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("The operation premaped can't get instance of " + classFile.getName());
        } catch (IllegalAccessException e) {
            System.out.println("The operation premaped can't get instance of " + classFile.getName());
        }
    }
    
    /**
     * A Static method to obtain the copy in memory of the object with the OperationsMap.
     * The idea is not calcule again every time.
     * @return {StringOperationsMap} Return the last instance OperationsMap Object placed in memory.
     */
    public static StringOperationsMap getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new StringOperationsMap();
            return instance;
        }
    }
    
    /**
     * This method return the string operations map.
     * @return {Hashtable<String, Operation>} Return the Hashtable with the string operations map..
     */
    public Hashtable<String, Operation> getAlgorithmMap() {
        return this.operationsMap;
    }
}
