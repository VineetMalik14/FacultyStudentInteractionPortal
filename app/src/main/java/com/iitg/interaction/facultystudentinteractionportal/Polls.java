package com.iitg.interaction.facultystudentinteractionportal;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Polls {
    public String question;
//    public ArrayList<String> options;
//    public Integer[] optionvotes;
    public ArrayList<Options> options;
    public ArrayList<String> users;
    public Integer totalvotes=0;
    public boolean isactive = true;

    public Polls()
    {

    }

    public Polls(String question, @NonNull ArrayList<String> options) {
        this.question = question;
        this.options = new ArrayList<>();
        this.users = new ArrayList<>();
        for(String s : options)
        {
            this.options.add(new Options(s));
        }
        this.isactive = true;
        this.totalvotes = 0;


    }

    public void closePoll()
    {
        isactive=false;
    }

    public void addvote(Integer index,String username)
    {
        if(this.users==null)
        {
            this.users = new ArrayList<>();
        }
        if(this.users.contains(username))
        {
           // return;
        }
        options.get(index).votes++;
        this.users.add(username);
        totalvotes++;
        for(Options op: this.options)
        {
            op.totalvotes=totalvotes;
        }

    }

    public Integer calculateTotalVotes()
    {
        Integer sum = 0;
        for (Options op : options) {
            sum+=op.votes;
        }
        totalvotes=sum;
        return  sum;
    }

}

class Options {
    public String optiontext;
    public int votes=0;
    public int totalvotes;

    public Options()
    {

    }

    public Options(String optiontext)
    {
        this.optiontext = optiontext;
        this.votes = 0;
    }


}
