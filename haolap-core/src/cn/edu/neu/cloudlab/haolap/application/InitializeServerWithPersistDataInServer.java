package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.initialize.Initializer;
import org.dom4j.DocumentException;

import java.io.IOException;

public class InitializeServerWithPersistDataInServer {
    public static void main(String args[]) {
        Initializer initilizer = new Initializer();
        try {
            initilizer.initializeServerWithPersistDataInServer();
        } catch (CubeXmlFileNotExistsException e) {
            e.printStackTrace();
        } catch (CubeAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CorrespondingDimensionNotExistsException e) {
            e.printStackTrace();
        } catch (SchemaAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CorrespondingSchemaNotExistsException e) {
            e.printStackTrace();
        } catch (CubeElementsNotExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
