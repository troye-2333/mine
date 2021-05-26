public enum state {
    open("open"),
    boom("boom"),
    covered("covered"),
    flag("flag");
    public String s;
    private state(String s){
        this.s=s;
    }
}
