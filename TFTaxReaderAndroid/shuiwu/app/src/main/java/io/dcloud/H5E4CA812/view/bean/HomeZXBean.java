package io.dcloud.H5E4CA812.view.bean;

import java.io.Serializable;

public class HomeZXBean implements Serializable {

    /**
     * News_ID : 9
     * News_Title : 税收新政02001
     * News_Intro : 税收新政02001
     * News_Content : &lt;11
     * News_Content_HTML : <p>111<p>
     * News_Author : 税收新政02001
     * News_Append_Time : 2020-05-15 10:50:50
     * News_Priority : 30
     * News_Del_Flag : false
     * News_Del_Time : null
     * News_AdminID : 11
     * News_Sort : 20
     */

    private int News_ID;
    private String News_Title;
    private String News_Intro;
    private String News_Content;
    private String News_Content_HTML;
    private String News_Author;
    private String News_Append_Time;
    private int News_Priority;
    private boolean News_Del_Flag;
    private Object News_Del_Time;
    private int News_AdminID;
    private int News_Sort;

    public int getNews_ID() {
        return News_ID;
    }

    public void setNews_ID(int News_ID) {
        this.News_ID = News_ID;
    }

    public String getNews_Title() {
        return News_Title;
    }

    public void setNews_Title(String News_Title) {
        this.News_Title = News_Title;
    }

    public String getNews_Intro() {
        return News_Intro;
    }

    public void setNews_Intro(String News_Intro) {
        this.News_Intro = News_Intro;
    }

    public String getNews_Content() {
        return News_Content;
    }

    public void setNews_Content(String News_Content) {
        this.News_Content = News_Content;
    }

    public String getNews_Content_HTML() {
        return News_Content_HTML;
    }

    public void setNews_Content_HTML(String News_Content_HTML) {
        this.News_Content_HTML = News_Content_HTML;
    }

    public String getNews_Author() {
        return News_Author;
    }

    public void setNews_Author(String News_Author) {
        this.News_Author = News_Author;
    }

    public String getNews_Append_Time() {
        return News_Append_Time;
    }

    public void setNews_Append_Time(String News_Append_Time) {
        this.News_Append_Time = News_Append_Time;
    }

    public int getNews_Priority() {
        return News_Priority;
    }

    public void setNews_Priority(int News_Priority) {
        this.News_Priority = News_Priority;
    }

    public boolean isNews_Del_Flag() {
        return News_Del_Flag;
    }

    public void setNews_Del_Flag(boolean News_Del_Flag) {
        this.News_Del_Flag = News_Del_Flag;
    }

    public Object getNews_Del_Time() {
        return News_Del_Time;
    }

    public void setNews_Del_Time(Object News_Del_Time) {
        this.News_Del_Time = News_Del_Time;
    }

    public int getNews_AdminID() {
        return News_AdminID;
    }

    public void setNews_AdminID(int News_AdminID) {
        this.News_AdminID = News_AdminID;
    }

    public int getNews_Sort() {
        return News_Sort;
    }

    public void setNews_Sort(int News_Sort) {
        this.News_Sort = News_Sort;
    }

    @Override
    public String toString() {
        return "HomeZXBean{" +
                "News_ID=" + News_ID +
                ", News_Title='" + News_Title + '\'' +
                ", News_Intro='" + News_Intro + '\'' +
                ", News_Content='" + News_Content + '\'' +
                ", News_Content_HTML='" + News_Content_HTML + '\'' +
                ", News_Author='" + News_Author + '\'' +
                ", News_Append_Time='" + News_Append_Time + '\'' +
                ", News_Priority=" + News_Priority +
                ", News_Del_Flag=" + News_Del_Flag +
                ", News_Del_Time=" + News_Del_Time +
                ", News_AdminID=" + News_AdminID +
                ", News_Sort=" + News_Sort +
                '}';
    }
}
