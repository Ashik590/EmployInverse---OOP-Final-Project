package logic;

import java.util.ArrayList;
import java.util.UUID;

public class Client extends Person{
    private ArrayList<String> offeredJobsID;

    public Client(String name, String phone, String email, String user_name, String password, double balance,Boolean save) {
        super(name, phone, email, user_name, password, balance);
        this.offeredJobsID = new ArrayList<>();

        if(save) FileFunctions.insertClient(user_name, password, name, phone, email, super.getBalance() ,offeredJobsID);
    }

    public Client(String name, String phone, String email, String user_name,String password,double balance, ArrayList<String> offeredJobsID, Boolean save) {
        this(name, phone, email, user_name, password,  balance,false);
        this.offeredJobsID = offeredJobsID;

        if(save) FileFunctions.insertClient(user_name, password, name, phone, email, balance ,offeredJobsID);
    }

    public Service createService(String title, String description,double price, boolean save){
        String ID = UUID.randomUUID().toString();
        Service service = new Service(ID, title, description, this.getUser_name(),price, save);
        this.offeredJobsID.add(ID);

        FileFunctions.updateClients(this);

        return service;
    }

    public int chooseProvider(ServiceProvider provider, Service service){

        if(!service.getStatus().equals("Incompleted")){
            System.out.println("The Job is not incompleted !");
            return 4;
        }

        Service targetService = null;


        for(String servId : this.offeredJobsID){
            if(servId.equals(service.getID())){
                targetService = service;

                if(provider.getCurrentJob() != null){
                    System.out.println("He is busy!");
                    return 2;
                }else if(!service.getApplicants_userName().contains(provider.getUser_name())){
                    System.out.println("He didnt apply for this job!");
                    return 5;
                }

                break;
            }
        }

        if(targetService != null){
            boolean haveMoney = reduceBalance(targetService.getPrice());

            FileFunctions.updateClients(this);

            if(haveMoney){
                targetService.setProvider(provider.getUser_name());
                targetService.setStatus("On-going");
                targetService.removeApplicant(provider.getUser_name());
                provider.setCurrentJob(targetService);

                return 1;
            }else{
                System.out.println("Client doesn't have enough money!");

                return 3;
            }

        }else
            throw new IllegalArgumentException("The Service doesn't belong to the client !");
    }

    public void deleteApplicant(Service service, ServiceProvider provider){
        service.removeApplicant(provider.getUser_name());
    }

    public void cancellationRequest(Service service){
        if(service.getStatus().equals("On-going"))
            service.setStatus("On-request-cancellation");
        else
            throw new IllegalArgumentException("Status of the Service is "+ service.getStatus());
    }

    public ArrayList<String> getOfferedJobsID() {
        return offeredJobsID;
    }

    public void acceptDelivery(Service service){
        if(offeredJobsID.contains(service.getID())){
            if(service.getStatus().equals("Delivered")){
                service.setStatus("Completed");
                ServiceProvider sp = service.getProvider();

                sp.addBalance(service.getPrice());
                sp.setCurrentJob(null);
                sp.addCompletedJob(service.getID());
                service.removeApplicant(sp.getUser_name());

                FileFunctions.updateProvider(sp);
            }
            else
                throw new IllegalArgumentException("Status of the Service is "+ service.getStatus());
        }else
            throw new IllegalArgumentException("The Job doesn't belong to the client !");
    }

    public ArrayList<Service> getOfferedJobs(){
        ArrayList<Service> offeredJobs = new ArrayList<>();

        for(String jobID : offeredJobsID){
            offeredJobs.add(FileFunctions.searchService(jobID));
        }

        return offeredJobs;
    }

    public void deleteJob(Service service){
        offeredJobsID.remove(service.getID());
        FileFunctions.updateClients(this);

        FileFunctions.deleteService(service);
    }

    @Override
    public String toString() {
        return super.toString() + "Number of job posted : " + this.offeredJobsID.size();
    }
}
