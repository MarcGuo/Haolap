package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.CubeElement;
import cn.edu.neu.cloudlab.haolap.cube.Page;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.PageFullException;
import cn.edu.neu.cloudlab.haolap.exception.PersistErrorException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CubeElementsInitialization extends InitializationAbstract
        implements Initialization {

    String baseCubeIdentifier = SystemConf.getBaseCubeIdentifier();
    private long numberOfElements;
    private double dataRange = 40.0;
    private Random r;

    public CubeElementsInitialization(Initialization next) {
        super(next);
        this.r = new Random();
    }

    @Override
    protected void initialize() throws PersistErrorException,
            PageFullException, IOException, CubeNotExistsException,
            SchemaNotExistsException {
        this.numberOfElements = CubeHelper
                .getNumberOfElements(baseCubeIdentifier);
        System.out.println("numberOfElements: " + numberOfElements);
        persist(baseCubeIdentifier);
    }

    private void persist(String cubeIdentifier) throws PersistErrorException,
            PageFullException, IOException, SchemaNotExistsException,
            CubeNotExistsException {

        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(cubeIdentifier);

        // numberOfDimensions
        int numberOfDimensions = schema.getDimensions().size();

        // numberOfPages & pageLengths & numberOfPageSegmentsInDimension &
        // dimensionLengths
        long numberOfPages = PageHelper.getNumberOfPages(schema);
        long pageLengths[] = PageHelper.getPageLengths(schema);
        long numberOfPageSegmentsInDimension[] = PageHelper
                .getNumberOfPageSegmentsInDimensions(schema);
        long dimensionLengths[] = DimensionHelper.getDimensionLengths(schema);
        System.out.println("numberOfPages:" + numberOfPages);
        System.out.println("dimensionLength "
                + Arrays.toString(dimensionLengths));
        System.out.println("pageLengths:" + Arrays.toString(pageLengths));
        System.out.println("numberOfPageSegmentsInDimension:"
                + Arrays.toString(numberOfPageSegmentsInDimension));

        Page page;
        // double data;
        for (long pageNo = 0; pageNo < numberOfPages; pageNo += 1) {
            // construct a page
            List<CubeElement<Double>> elementList = new ArrayList<CubeElement<Double>>();
            long pointForPage[] = Serializer.deserialize(
                    numberOfPageSegmentsInDimension,
                    new BigInteger(String.valueOf(pageNo)));
            long pointForElement[][] = new long[2][numberOfDimensions];
            for (int i = 0; i < numberOfDimensions; i += 1) {
                pointForElement[0][i] = pointForPage[i] * pageLengths[i];
                pointForElement[1][i] = pointForElement[0][i] + pageLengths[i]
                        - 1;
            }
            System.out.println("Page:\t" + pageNo + "\tstart:\t"
                    + Arrays.toString(pointForElement[0]) + "\tend:\t"
                    + Arrays.toString(pointForElement[1]));
            long[] start = pointForElement[0];
            long[] end = pointForElement[1];
            long[] point = new long[numberOfDimensions];
            long[] lengths = pageLengths;
            int pointLength = numberOfDimensions;
            int pointAmount = 1;
            for (int i = 0; i < pointLength; i++) {
                pointAmount *= end[i] - start[i] + 1;
            }
            for (int i = 0; i < point.length; i++) {
                point[i] = start[i];
            }

            elementList.add(new CubeElement<Double>(Serializer.serialize(
                    dimensionLengths, point).longValue(), r.nextDouble()
                    * dataRange));
            for (int i = 0; i < pointAmount - 1; i++) {
                point[pointLength - 1]++;
                if (point[pointLength - 1] >= lengths[pointLength - 1]) {
                    for (int j = pointLength - 1; j >= 0; j--) {
                        if (point[j] >= lengths[j] && j > 0) {
                            point[j] = 0;
                            point[j - 1]++;
                        } else {
                            break;
                        }
                    }
                }
                elementList.add(new CubeElement<Double>(Serializer.serialize(
                        dimensionLengths, point).longValue(), this.r
                        .nextDouble() * dataRange));
            }

            page = new Page(cubeIdentifier, pageNo, elementList);
            page.persist();
        }
    }
}
