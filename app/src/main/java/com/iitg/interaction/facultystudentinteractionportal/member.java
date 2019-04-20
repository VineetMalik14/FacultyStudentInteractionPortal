//package com.example.myapplication;
package com.iitg.interaction.facultystudentinteractionportal;

public class member {
    private String QuesName;
    private String Op1Name;
    private String Op2Name;
    private String Op3Name;
    private String Op4Name;
    private String Op0Name;
    private String key;
    private int Op1value;
    private int Op2value;
    private int Op3value;
    private int Op4value;
    private int Op0value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public member(){
        this.Op1value = 0;
        this.Op2value = 0;
        this.Op3value = 0;
        this.Op4value = 0;
        this.Op0value = 0;
        this.Op1Name = "";
        this.Op2Name = "";
        this.Op3Name = "";
        this.Op4Name = "";
        this.Op0Name = "";
    }

    public String getQuesName() {
        return QuesName;
    }

    public void setQuesName(String quesName) {
        QuesName = quesName;
    }

    public String getOp1Name() {
        return Op1Name;
    }

    public void setOp1Name(String op1Name) {
        Op1Name = op1Name;
    }

    public String getOp2Name() {
        return Op2Name;
    }

    public void setOp2Name(String op2Name) {
        Op2Name = op2Name;
    }

    public String getOp3Name() {
        return Op3Name;
    }

    public void setOp3Name(String op3Name) {
        Op3Name = op3Name;
    }

    public String getOp4Name() {
        return Op4Name;
    }

    public void setOp4Name(String op4Name) {
        Op4Name = op4Name;
    }

    public String getOp0Name() {
        return Op0Name;
    }

    public void setOp0Name(String op5Name) {
        Op0Name = op5Name;
    }

    public int getOp1value() {
        return Op1value;
    }

    public void setOp1value(int op1value) {
        Op1value = op1value;
    }

    public int getOp2value() {
        return Op2value;
    }

    public void setOp2value(int op2value) {
        Op2value = op2value;
    }

    public int getOp3value() {
        return Op3value;
    }

    public void setOp3value(int op3value) {
        Op3value = op3value;
    }

    public int getOp4value() {
        return Op4value;
    }

    public void setOp4value(int op4value) {
        Op4value = op4value;
    }

    public int getOp0value() {
        return Op0value;
    }

    public void setOp0value(int op5value) {
        Op0value = op5value;
    }
}
