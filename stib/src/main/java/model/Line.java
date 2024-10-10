package model;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<Integer> lines;

    public Line(){
        lines = new ArrayList<>();
    }

    public void setLines(List lines){
        this.lines = lines;
    }

    public void addLine(int line){
        lines.add(line);
    }

    public List<Integer> getLines(){
        return lines;
    }

    @Override
    public String toString() {
        String out = "[";
        for (int i = 0;i<lines.size()-1;i++) {
            out += lines.get(i) + ",";
        }
        out += lines.get(lines.size()-1)+"]";
        return out;
    }
}
