package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.initialize.Initializer;

import java.io.IOException;

public class InitializeRandomData {
    public static void main(String args[]) {
        CubeConfiguration.getConfiguration().addResource("conf/cube-default.xml");
        Initializer initilizer = new Initializer();
        try {
            initilizer.initializeRandomData();
        } catch (SchemaAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CubeAlreadyExistsException e) {
            e.printStackTrace();
        } catch (PersistErrorException e) {
            e.printStackTrace();
        } catch (PageFullException e) {
            e.printStackTrace();
        } catch (SchemaNotExistsException e) {
            e.printStackTrace();
        } catch (CubeNotExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
