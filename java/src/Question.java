public class Question
{
    protected String intitule;
    protected int reponse;

    public Question(String intitule, int reponse) {
        this.intitule = intitule;
        this.reponse = reponse;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getReponse() {
        return reponse;
    }

    public void setReponse(int reponse) {
        this.reponse = reponse;
    }

    public String toString() {
        return intitule + " - " + reponse;
    }
}