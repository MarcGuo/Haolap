package cn.edu.neu.cloudlab.dataloader;

import cn.edu.neu.cloudlab.haolap.exception.*;

import java.io.IOException;

public class InitializeRandomData {
    public static void main(String args[]) {

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
