package com.siwa.helpers;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.exceptions.AlgorithmDuplicateName;
import com.siwa.interfaces.SimilarityAlgorithm;

/**
 * This class contain the Compare Algorithm map to get the compare criterias availables in the system.
 */
public class CompareAlgorithmMap {
    /**
     * This attribute save the instance of object in memory to rescue in every time of app execution.
     */
    private static CompareAlgorithmMap instance = null;
    /**
     * The attributes set the package name and the hashtable with the class maped.
     */
    private String algorithmPackage = "com.siwa.algorithms";
    private Hashtable<String, SimilarityAlgorithm> algorithmMap;
    
    /**
     * The constructor of class. Its call's from root App. If needed in other place, 
     * please call across static getInstance method.
     */
    
    public CompareAlgorithmMap() {
        this.algorithmMap = new Hashtable<String, SimilarityAlgorithm>();
        this.mapAlgorithms();
        // Save the instance in static class declaration
        instance = this;
    }
    
    /**
     * This method iterate the classes in the package set in AlgorithmPackage. 
     * Each class is verified about it's or not a Compare Algorithm class.
     */
    private void mapAlgorithms() {
          ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
          URL resource = classLoader.getResource(this.algorithmPackage.replace(".", "/"));
          File packageDir = new File(resource.getFile());
          File[] classes = packageDir.listFiles();
          for (File classFile : classes) {
              if (!classFile.isDirectory()) this.checkAlgorithm(classFile);
          }
    }
    
    /**
     * This method identify if the provide class file is a SimilarityAlgorithm implementation.
     * If is a Similarity Algorithm class, the method pass them to checkDeclaredAlgorithm method.
     * @param {File} classFile Provide the class file that we want verify is a Similarity Algorithm implementation.
     */
    private void checkAlgorithm(File classFile) {
        try {
            Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(this.algorithmPackage + 
                  "." + classFile.getName().replaceAll(".class$", ""));
            if (SimilarityAlgorithm.class.isAssignableFrom(cls)) {
                this.checkDeclaredAlgorithm(classFile, cls);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The class premaped not in the package " + classFile.getName());
        }
    }
    
    /**
     * This method identify if the provide class file is defined like a CompareAlgorithm by annotation.
     * If is a Compare Algorithm class, the method add to algorithmMap.
     * @param {File} classFile Provide the class file that we want verify is a Compare Algorithm class.
     * @param {Class<?>} cls Provide class definition that we want to inspect if have the CompareAlgorithm annotation.
     */
    private void checkDeclaredAlgorithm(File classFile, Class<?> cls) {
        try {
            CompareAlgorithm algorithm = cls.getAnnotation(CompareAlgorithm.class);
            if (algorithm != null) {
                if (!this.algorithmMap.containsKey(algorithm.name())) {
                    this.algorithmMap.put(algorithm.name(), (SimilarityAlgorithm) cls.newInstance());
                } else {
                    throw new AlgorithmDuplicateName("The algorithm " + algorithm.name() + " in " +
                          cls.getName() + "class have a duplicated named");
                }
            }
        } catch (AlgorithmDuplicateName e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("The class premaped can't get instance of" + classFile.getName());
        } catch (IllegalAccessException e) {
            System.out.println("The class premaped can't get instance of" + classFile.getName());
        }
    }
    
    /**
     * A Static method to obtain the copy in memory of the object with the algorithmMap.
     * The idea is not calcule again every time.
     * @return {CompareAlgorithmMap} Return the last instance CompareAlgorithmMap Object placed in memory.
     */
    public static CompareAlgorithmMap getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new CompareAlgorithmMap();
            return instance;
        }
    }
    
    /**
     * This method return the algorithms map.
     * @return {Hashtable<String, SimilarityAlgorithm>} Return the Hashtable with the algorithms map.
     */
    public Hashtable<String, SimilarityAlgorithm> getAlgorithmMap() {
        return this.algorithmMap;
    }
}
