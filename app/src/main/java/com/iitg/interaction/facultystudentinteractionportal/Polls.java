package com.iitg.interaction.facultystudentinteractionportal;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import static com.iitg.interaction.facultystudentinteractionportal.UserInfo.username;

public class Polls {
    public String creater;
    public String creatertype;
    public String question;
    public String uniqueid;
    public Integer usernum;
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
        this.uniqueid = String.valueOf(Calendar.getInstance().getTimeInMillis())+ username;
        this.options = new ArrayList<>();
        this.creater= username;
        this.creatertype = UserInfo.usertype;
        for(String s : options)
        {
            this.options.add(new Options(s));
        }
        this.isactive = true;
        this.totalvotes = 0;
        this.usernum=0;

    }

    public void closePoll()
    {
        isactive=false;
    }
    public void openPoll(){isactive=true;}

    public boolean addvote(Integer index,String username)
    {
        if(this.users==null)
        {
            this.users = new ArrayList<>();
        }

        if(this.users.contains(username))
        {
            return false;
        }

        if(this.options.get(index).userslist==null)
        {
            this.options.get(index).userslist = new ArrayList<>();
        }


        this.options.get(index).votes++;
        this.options.get(index).userslist.add(username);
        totalvotes++;
        this.users.add(username);
        for(Options op: this.options)
        {
            op.totalvotes=totalvotes;
        }

        return true;

    }

    public void removeuser(String username)
    {
        if(this.users!=null && this.users.contains(username))
        {
            this.users.remove(username);
            this.totalvotes--;
            for(Options op : this.options)
            {
                if(op.userslist!=null && op.userslist.contains(username))
                {
                    op.votes--;
                    op.userslist.remove(username);

                }
            }
            calculateTotalVotes();
        }
    }


    public Integer calculateTotalVotes()
    {
        Integer sum = 0;
        for (Options op : this.options) {
            sum+=op.votes;
        }
        for (Options op: this.options)
        {
            op.totalvotes=sum;
        }
        totalvotes=sum;
        return  sum;
    }


}


class Options {
    public String optiontext;
    public int votes=0;
    public int totalvotes=0;
    public Integer usernum;
    public ArrayList<String> userslist;

    public Options()
    {

    }

    public Options(String optiontext)
    {
        this.optiontext = optiontext;
        this.votes = 0;
        this.usernum=0;
        this.userslist=new ArrayList<>();
        this.totalvotes = 0;
    }


}
