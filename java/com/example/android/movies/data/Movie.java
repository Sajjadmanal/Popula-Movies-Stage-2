package com.example.android.movies.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    //Movie id
    private String id;
    //Movie title
    private String title;
    //Movie poster path
    private String poster;
    //Movie back poster
    private String back_poster;
    //Movie summary
    private String overview;
    //Movie release date
    private String date;
    //Movie vote average
    private Double vote_avg;
    //Movie total votes
    private int total_votes;

    public Movie(){ }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public void setPoster(String poster){ this.poster = poster; }

    public String getBack_poster() {return back_poster;}

    public void setBack_poster(String back_poster) {this.back_poster = back_poster;}

    public String getPoster(){ return poster; }

    public String getOverview() {return overview;}

    public void setOverview(String overview) { this.overview = overview; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public Double getVote_avg() { return vote_avg; }

    public void setVote_avg(Double vote_avg) { this.vote_avg = vote_avg; }

    public int getTotal_votes() { return total_votes; }

    public void setTotal_votes(int total_votes) { this.total_votes = total_votes; }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster = in.readString();
        back_poster = in.readString();
        overview = in.readString();
        date = in.readString();
        vote_avg = in.readByte() == 0x00 ? null : in.readDouble();
        total_votes = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(back_poster);
        dest.writeString(overview);
        dest.writeString(date);
        if (vote_avg == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(vote_avg);
        }
        dest.writeInt(total_votes);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
