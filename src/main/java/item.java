public class item {
    //private static int counter;
    private int num;
    private String name;
    private boolean marked;

    public item(String name, int num){
        this.name = name;
        this.num = num;
        this.marked = false;
    }

    public void setDone(){
        this.marked = true;
    }

    public void setunDone(){
        this.marked = false;
    }

    @Override
    public String toString(){
        String temp = marked ? num + ".[X]" + name : num + ".[]" + name;
        return temp;
    }
    public String mark(){
        String temp ="[X]" + name;
        return "Nice! I've marked this task as done:\n" + temp;
    }
    public String unmark(){
        String temp ="[ ]" + name;
        return "OK, I've marked this task as not done yet:\n" + temp;
    }

}
