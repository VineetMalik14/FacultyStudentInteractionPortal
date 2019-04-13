package com.iitg.interaction.facultystudentinteractionportal;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Polls {
    public String question;
    public ArrayList<String> options;
    public Integer[] optionvotes;
    public Integer totalvotes=0;
    public boolean isactive = true;


    public Polls(String question, @NonNull ArrayList<String> options) {
        this.question = question;
        this.options = options;
        this.isactive = true;
        this.totalvotes = 0;
        this.optionvotes = new Integer[options.size()];
        for(int i = 0 ;i<options.size() ;i++)
        {
            optionvotes[i]=0;
        }


    }

    public void closePoll()
    {
        isactive=false;
    }

    public void addvote(Integer index)
    {
        optionvotes[index]++;
        totalvotes++;
    }

    public Integer calculateTotalVotes()
    {
        Integer sum = 0;
        for (Integer vote : optionvotes) {
            sum+=vote;
        }
        totalvotes=sum;
        return  sum;
    }

}
