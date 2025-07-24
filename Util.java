package uc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Created by "P.Khodaparast" on 2017-06-21.
 */
public class Util {
    public Util() throws FileNotFoundException {
    }

    FileOutputStream fileOutputStream = new FileOutputStream("C:/dotNetDissimMatrix.csv", true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    static String javaTags = "nullpointerexception,exception,io,file,file-io,performance,jvm,multithreading,concurrency,sockets,scala,jni,c++,c#,php,python,security,applet,encryption,osx,linux,windows,hadoop,image,awt,swing,jtable,user-interface,jpanel,jframe,javafx,swt,rest,jersey,json,web-services,soap,xml,jaxb,serialization,http,apache,android,maven,intellij-idea,eclipse,jar,ant,netbeans,unit-testing,junit,selenium,gwt,google-app-engine,jboss,java-ee,jsf,spring-mvc,spring,spring-security,jpa,hibernate,servlets,jsp,tomcat,struts2,jquery,javascript,html,mysql,jdbc,sql,oracle,sqlite,database,logging,log4j,oop,design-patterns,inheritance,methods,class,object,reflection,generics,annotations,interface,list,collections,arrays,arraylist,sorting,algorithm,hashmap,loops,parsing,date,string,regex,libgdx";
    static String phpTags = " zend-framework,zend-framework2,symfony2,doctrine2,doctrine,arrays,multidimensional-array,sorting,search,mysql,sql,database,mysqli,table,select,pdo, sql-server, pagination,phpmyadmin, mongodb,android,java,wordpress,wordpress-plugin,javascript,jquery, ajax,json,magento,linux,performance,caching, cron,xml,parsing,simplexml, dom,laravel,laravel-4,oop,function,variables,class,object, include,foreach,loops,if-statement,smarty, codeigniter,session,email, cookies, login, authentication,image, gd,html, forms, css, validation, html5,facebook, facebook-graph-api, twitter, paypal,apache, .htaccess, mod-rewrite, redirect, xampp,joomla, drupal,file-upload, file, upload, pdf, utf-8, csv,regex, string, preg-replace, preg-match,date, datetime, security, encryption, python, curl, api, rest,soap, web-services,yii,cakephp, phpunit, post, get, http, url";
    static String dotNetTags = "sql-server,sql,database,ado.net,mysql,sql-server-2008,entity-framework-4,entity-framework,nhibernate,oracle,xml,xml-serialization,excel,winforms,datagridview,gridview,image,datetime,azure,powershell,c#,c#-4.0,design-patterns,oop,architecture,dependency-injection,generics,dll,com,assemblies,msbuild,deployment,interop,php,exception,exception-handling,wpf,xaml,data-binding,events,mvvm,user-interface,user-controls,java,vb.net,c++,clr,c++-cli,garbage-collection,linq,linq-to-sql,lambda,security,encryption,mono,.net-2.0,.net-4.0,.net-3.5,visual-studio,visual-studio-2010,visual-studio-2008,visual-studio-2012,regex,string,multithreading,asynchronous,task-parallel-library,async-await,silverlight,windows-phone-7,collections,arrays,list,json,javascript,html,jquery,ajax,windows,windows-services,winapi,sockets,compact-framework,wcf,unit-testing,reflection,debugging,performance,f#,iis,asp.net-mvc,asp.net-mvc-3,asp.net-mvc-4,asp.net-web-api,asp.net,soap,rest,serialization,web-services,configuration";
//    static String[] allTags = javaTags.split(",");
//    static String[] allTags = phpTags.split(",");
    static String[] allTags = dotNetTags.split(",");

    static Set<String> tagSet = new HashSet<>();
    static Multimap<String, String> tagQuestionsMap = new HashMultimap<>();


    static {
        for (int i = 0; i < allTags.length; i++) {
            tagSet.add(allTags[i].trim());
        }
    }

    public void questionOFTags(String tags, String id) throws IOException {

        String resultTags = tags;
        resultTags = resultTags.replace("<", "");
        resultTags = resultTags.replace(">", ",");
        String[] tagName = resultTags.split(",");

        for (int i = 0; i < tagName.length; i++) {
            String tag = tagName[i].trim();
            if (tagSet.contains(tag))
                tagQuestionsMap.put(tag, id);
        }
    }

    public static Map getDissimilarityMap(Multimap multimap) {
        Multimap<String, String> tagsMap = multimap;//tag names and questions that they occur
        Map<String, Float> dissimilarityMap = new HashMap<>();


        // dissimilarity(i,j) = (|i ∪ j| − |i ∩ j|) / |i ∪ j|
        for (int i = 0; i < allTags.length; i++) {

            Collection<String> tagQIdsi = tagsMap.get(allTags[i]);

            for (int j = 0; j < allTags.length; j++) {
                Collection<String> tagQIdsj = tagsMap.get(allTags[j]);

                if (allTags[i].equalsIgnoreCase(allTags[j])) {//if both are the same
                    dissimilarityMap.put(allTags[i] + "_" + allTags[j], 0.0f);
                } else if (tagQIdsi.size() != 0 && tagQIdsj.size() != 0) {
                    Collection<String> sorateCasr = new ArrayList<>();
                    Set<String> unionSet = new HashSet<>();
                    Set<String> intersectionSet = new HashSet<>();

                    intersectionSet.addAll(tagQIdsi);
                    intersectionSet.retainAll(tagQIdsj);
                    unionSet.addAll(tagQIdsi);
                    unionSet.addAll(tagQIdsj);
                    sorateCasr.addAll(unionSet);
                    sorateCasr.removeAll(intersectionSet);
                    float dis = 0.0f;
                    float a = sorateCasr.size();
                    float b = unionSet.size();
                    dis = a / b;
                    dissimilarityMap.put(allTags[i] + "_" + allTags[j], dis);
                } else {
                    dissimilarityMap.put(allTags[i] + "_" + allTags[j], 1f);
                }
            }
        }
        System.out.println(dissimilarityMap);
        return dissimilarityMap;
    }

    public void makeDissimMatrix(Map<String, Float> map) throws IOException {
        Map<String, Float> result = map;
//        outputStreamWriter.write(" ,nullpointerexception,exception,io,file,file-io,performance,jvm,multithreading,concurrency,sockets,scala,jni,c++,c#,php,python,security,applet,encryption,osx,linux,windows,hadoop,image,awt,swing,jtable,user-interface,jpanel,jframe,javafx,swt,rest,jersey,json,web-services,soap,xml,jaxb,serialization,http,apache,android,maven,intellij-idea,eclipse,jar,ant,netbeans,unit-testing,junit,selenium,gwt,google-app-engine,jboss,java-ee,jsf,spring-mvc,spring,spring-security,jpa,hibernate,servlets,jsp,tomcat,struts2,jquery,javascript,html,mysql,jdbc,sql,oracle,sqlite,database,logging,log4j,oop,design-patterns,inheritance,methods,class,object,reflection,generics,annotations,interface,list,collections,arrays,arraylist,sorting,algorithm,hashmap,loops,parsing,date,string,regex,libgdx");
//        outputStreamWriter.write(",zend-framework,zend-framework2,symfony2,doctrine2,doctrine,arrays,multidimensional-array,sorting,search,mysql,sql,database,mysqli,table,select,pdo, sql-server, pagination,phpmyadmin, mongodb,android,java,wordpress,wordpress-plugin,javascript,jquery, ajax,json,magento,linux,performance,caching, cron,xml,parsing,simplexml, dom,laravel,laravel-4,oop,function,variables,class,object, include,foreach,loops,if-statement,smarty, codeigniter,session,email, cookies, login, authentication,image, gd,html, forms, css, validation, html5,facebook, facebook-graph-api, twitter, paypal,apache, .htaccess, mod-rewrite, redirect, xampp,joomla, drupal,file-upload, file, upload, pdf, utf-8, csv,regex, string, preg-replace, preg-match,date, datetime, security, encryption, python, curl, api, rest,soap, web-services,yii,cakephp, phpunit, post, get, http, url");
        outputStreamWriter.write(",sql-server,sql,database,ado.net,mysql,sql-server-2008,entity-framework-4,entity-framework,nhibernate,oracle,xml,xml-serialization,excel,winforms,datagridview,gridview,image,datetime,azure,powershell,c#,c#-4.0,design-patterns,oop,architecture,dependency-injection,generics,dll,com,assemblies,msbuild,deployment,interop,php,exception,exception-handling,wpf,xaml,data-binding,events,mvvm,user-interface,user-controls,java,vb.net,c++,clr,c++-cli,garbage-collection,linq,linq-to-sql,lambda,security,encryption,mono,.net-2.0,.net-4.0,.net-3.5,visual-studio,visual-studio-2010,visual-studio-2008,visual-studio-2012,regex,string,multithreading,asynchronous,task-parallel-library,async-await,silverlight,windows-phone-7,collections,arrays,list,json,javascript,html,jquery,ajax,windows,windows-services,winapi,sockets,compact-framework,wcf,unit-testing,reflection,debugging,performance,f#,iis,asp.net-mvc,asp.net-mvc-3,asp.net-mvc-4,asp.net-web-api,asp.net,soap,rest,serialization,web-services,configuration");
        for (int i = 0; i < allTags.length; i++) {
            outputStreamWriter.write("\n" + allTags[i]);
            for (int j = 0; j < allTags.length; j++) {
                outputStreamWriter.write("," + String.valueOf(result.get(allTags[i] + "_" + allTags[j])));
            }
        }
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
