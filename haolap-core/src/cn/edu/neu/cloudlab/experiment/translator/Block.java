package cn.edu.neu.cloudlab.experiment.translator;

public class Block {

    private String[] blocks;
    private int middle;

    public Block(String blockStr) {
        String delimiter = ",";
        blockStr = blockStr.replaceAll(" +", delimiter);
        this.blocks = blockStr.split(delimiter);
        this.middle = this.blocks.length / 2;
    }

    public boolean match(String keyWord) {
        return keyWord.equals(this.blocks[3]);
    }

    public String scan(String title) {
        for (int i = 0; i < this.blocks.length; i++) {
            if (title.equals(this.blocks[i])) {
                return this.blocks[i + this.middle];
            }
        }
        return null;
    }

}
