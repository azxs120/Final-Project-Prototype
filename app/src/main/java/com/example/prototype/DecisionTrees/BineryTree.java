package com.example.prototype.DecisionTrees;

public class BineryTree {
    private int nodeID;
    private String questOrAns = null;
    private BineryTree yesBranch = null;
    private BineryTree noBranch = null;

    public BineryTree( String newQuestAns) {
        questOrAns = newQuestAns;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public String getQuestOrAns() {
        return questOrAns;
    }

    public void setQuestOrAns(String questOrAns) {
        this.questOrAns = questOrAns;
    }

    public BineryTree getYesBranch() {
        return yesBranch;
    }

    public void setYesBranch(BineryTree yesBranch) {
        this.yesBranch = yesBranch;
    }

    public BineryTree getNoBranch() {
        return noBranch;
    }

    public void setNoBranch(BineryTree noBranch) {
        this.noBranch = noBranch;
    }
}
