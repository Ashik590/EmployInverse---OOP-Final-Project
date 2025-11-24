package pack;
import java.util.ArrayList;

public class ServiceProvider extends Person {
    private String currentJobID;
    private ArrayList<String> completedJobsID;

    public ServiceProvider(String name, String phone, String email, String user_name, String password, double balance,boolean save){
        super(name, phone, email, user_name, password, balance);
        this.currentJobID = "None";
        this.completedJobsID = new ArrayList<String>();

        if(save){
            FileFunctions.insertProvider(user_name, password, name, phone, email, balance, currentJobID, completedJobsID);
        }
    }
    public ServiceProvider(String name, String phone, String email, String user_name, String password, double balance,String currentJobID, ArrayList<String> completedJobsID, boolean save){
        this(name, phone, email, user_name, password,balance,false);
        this.currentJobID = currentJobID;
        this.completedJobsID = completedJobsID;

        if(save){
            FileFunctions.insertProvider(user_name, password, name, phone, email, balance, currentJobID, completedJobsID);
        }
    }

    public void applyForJob(Service job){
        if(this.currentJobID.equals("None"))
            job.addApplicant(this.getUser_name());
        else
            throw new IllegalArgumentException("The Service Provider has already a job");

    }

    public void deliverForJob(){
        if(!this.currentJobID.equals("None")){
            Service currentJob = getCurrentJob();
            currentJob.setStatus("Delivered");
        }else
            throw new IllegalArgumentException("The Service Provider doesn't have any Job !");

    }

    public Service getCurrentJob() {
        return FileFunctions.searchService(this.currentJobID);
    }

    public ArrayList<String> getCompletedJobsID() {
        return completedJobsID;
    }

    void setCurrentJob(Service currentJob) {
        if(currentJob != null)
            this.currentJobID = currentJob.getID();
        else
            this.currentJobID = "None";

        FileFunctions.updateProvider(this);
    }

    public String getCurrentJobID() {
        return currentJobID;
    }

    void addCompletedJob(String jobID){
        this.completedJobsID.add(jobID);
        FileFunctions.updateProvider(this);
    }

    public void denyCancellationRequest(){
        Service currentJob = getCurrentJob();
        if(!this.currentJobID.equals("None") && currentJob.getStatus().equals("On-request-cancellation"))
            currentJob.setStatus("On-going");

        else{
            String errMsg;
            if(this.currentJobID.equals("None"))
                errMsg = "The Service Provider has no Job !";
            else
                errMsg = "The Service didn't get Cancellation Request from Client !";

            throw new IllegalArgumentException(errMsg);
        }
    }



    public void cancelJob(){
        Service currentJob = getCurrentJob();
        if(!this.currentJobID.equals("None")){

            currentJob.setStatus("Incompleted");
            currentJob.setProvider("None");
            this.currentJobID = "None";
            currentJob.getClient().addBalance(currentJob.getPrice());

            FileFunctions.updateProvider(this);
            FileFunctions.updateClients(currentJob.getClient());
        }else
            throw new IllegalArgumentException("The Service Provider doesn't have any Job !");
    }

    public ArrayList<Service> getCompletedJobs(){
        ArrayList<Service> completedJobs = new ArrayList<>();

        for(String jobID : completedJobsID){
            Service service = FileFunctions.searchService(jobID);
            completedJobs.add(service);
        }

        return completedJobs;
    }

    public ArrayList<Service> getAppliedJobs(){
        ArrayList<Service> appliedJobs = new ArrayList<>();
        ArrayList<Service> all_jobs = FileFunctions.getAllServices();

        for(Service job : all_jobs){
            if(job.getApplicants_userName().contains(this.getUser_name()))
                appliedJobs.add(job);
        }

        return appliedJobs;
    }


    @Override
    public String toString() {
        return super.toString() + "CurrentJob : " + this.currentJobID + "\n" + "Number of completed jobs : " + completedJobsID.size() + "\n";
    }
}
