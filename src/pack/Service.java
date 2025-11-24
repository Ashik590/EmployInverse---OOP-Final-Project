package pack;
import java.util.ArrayList;

public class Service  {
    private final String ID;
    private final String title;
    private final String description;
    private ArrayList<String> applicants;
    private String status; // Incompleted, Completed, On-going, Delivered,On-request-cancellation
    private String provider_userName;
    private final String client_userName;
    private final double price;

    public Service(String ID, String title, String description, String client_userName,double price ,boolean save) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.applicants = new ArrayList<>();
        this.status = "Incompleted";
        this.provider_userName = "None";
        this.client_userName = client_userName;
        this.price = price;

        if(save) {
            FileFunctions.insertService(ID, title, description, status, provider_userName, client_userName,price, applicants);
        }

    }

    public Service(String ID, String title, String description, String client_userName,double price, ArrayList<String> applicants,  String status,  String provider_userName, boolean save) {
        this(ID, title, description, client_userName,price, false);

        this.applicants = applicants;
        this.status = status;
        this.provider_userName = provider_userName;

        if(save) {
            FileFunctions.insertService(ID, title, description, status, provider_userName, client_userName,price, applicants);
        }
    }

    public void addApplicant(String applicant_userName){
        if(this.applicants.contains(applicant_userName))
            throw new IllegalArgumentException("The Service Provider has already applied to this job !!");
        else
            this.applicants.add(applicant_userName);

        FileFunctions.updateService(this);
    }

    public void removeApplicant(String applicant_userName){
        this.applicants.remove(applicant_userName);
        FileFunctions.updateService(this);
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ServiceProvider getProvider() {
        return FileFunctions.searchProvider(provider_userName);
    }

    void setProvider(String provider_userName) {
        this.provider_userName = provider_userName;
        FileFunctions.updateService(this);
    }

    public String getProvider_userName() {
        return provider_userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        FileFunctions.updateService(this);
    }

    public ArrayList<String> getApplicants_userName() {
        return applicants;
    }

    public ArrayList<ServiceProvider> getApplicants() {
        ArrayList<ServiceProvider> SPapplicants = new ArrayList<>();

        for (int i = 0; i < applicants.size(); i++) {
            SPapplicants.add(FileFunctions.searchProvider(applicants.get(i)));
        }

        return SPapplicants;
    }



    public String getClient_userName() {
        return client_userName;
    }

    public Client getClient() { return FileFunctions.searchClient(client_userName); }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return  "ID : " + this.getID() + "\n" +"Title : " + this.title + "\n" + "Description : "+ this.description + "\n" +"Client : "+ this.client_userName + "\n" + "Provider : " + provider_userName + "\n" + "Status : " + this.status+ "\n";
    }
}
