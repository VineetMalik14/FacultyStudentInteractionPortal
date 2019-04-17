package com.iitg.interaction.facultystudentinteractionportal;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Polls {
    public String creater;
    public String creatertype;
    public String question;
//    public ArrayList<String> options;
//    public Integer[] optionvotes;
    public ArrayList<Options> options;
    public ArrayList<userlist> users;
    public Integer totalvotes=0;
    public boolean isactive = true;

    public Polls()
    {

    }



    public Polls(String question, @NonNull ArrayList<String> options) {
        this.question = question;
        this.options = new ArrayList<>();
        this.users = new ArrayList<>();
        this.creater=UserInfo.username;
        this.creatertype = UserInfo.usertype;
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
    public void openPoll(){isactive=true;}

    public  void removeuser(String username)
    {
        for(userlist user: this.users)
        {
            if(user.username.equals(username))
            {
                this.options.get(user.index).votes--;
                this.calculateTotalVotes();
                this.users.remove(user);
                break;
            }
        }



        return ;
    }

    public boolean addvote(Integer index,String username)
    {
        if(this.users==null)
        {
            this.users = new ArrayList<>();
        }

        if(users.size()>0)
        for(int i=0;i<users.size();i++)
        {

            if(users.get(i).username.equals(username))
            {
                return false;
            }
           // Toast.makeText(,"You have already Polled!",Toast.LENGTH_LONG).show();

        }
        options.get(index).votes++;
        this.users.add(new userlist(index,username));
        totalvotes++;
        for(Options op: this.options)
        {
            op.totalvotes=totalvotes;
        }

        return true;

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

class userlist{
    public Integer index;
    public String username;

    public userlist(){

    }

    public userlist(Integer index,String username)
    {
        this.index = index;
        this.username = username;
    }
}

class Options {
    public String optiontext;
    public int votes=0;
    public int totalvotes=0;

    public Options()
    {

    }

    public Options(String optiontext)
    {
        this.optiontext = optiontext;
        this.votes = 0;
        this.totalvotes = 0;
    }


}
