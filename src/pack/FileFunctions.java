package pack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class FileFunctions {

    public static void insertService(String id, String title, String description,  String status, String provider, String client_userName,double price,ArrayList<String> applicants) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("services.txt", true));

            writer.write( id + "\n");
            writer.write( title + "\n");
            writer.write( description + "\n");
            writer.write( status + "\n");
            writer.write(Objects.requireNonNullElse(provider, "None") + "\n");
            writer.write( client_userName + "\n");
            writer.write( price + "\n");

            for(String s: applicants){
                writer.write( s + ",");
            }
            writer.write( "\n#\n");

            writer.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Service searchService(String id) {
        Service service = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader("services.txt"));

            String serviceID;

            while((serviceID = reader.readLine()) != null){
                String title , description, status, provider_userName, client_userName;
                double price;

                ArrayList<String> applicants;

                title = reader.readLine();
                description = reader.readLine();
                status = reader.readLine();
                provider_userName = reader.readLine();
                client_userName = reader.readLine();
                price  = Double.parseDouble(reader.readLine());
                String[] applicants2 = reader.readLine().split(",");
                applicants = new ArrayList<>(Arrays.asList(applicants2));
                if(applicants.getFirst().isEmpty()) applicants.removeFirst();


                reader.readLine();

                if(serviceID.equals(id)){
                    service = new Service(id, title, description, client_userName,price, applicants, status, provider_userName, false);
                    break;
                }
            }

            reader.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return service;
    }


    public static void insertClient(String userName, String password, String name, String phone, String email, double balance,ArrayList<String> offeredJobs) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt", true));

            writer.write( userName + "\n");
            writer.write( password + "\n");
            writer.write( name + "\n");
            writer.write( phone + "\n");
            writer.write( email + "\n");
            writer.write( balance + "\n");

            for(String s: offeredJobs){
                writer.write( s + ",");
            }

            writer.write( "\n#\n");

            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertProvider(String userName, String password, String name, String phone, String email,double balance, String currentJobID,  ArrayList<String> completedJobsID) {
        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter("providers.txt", true));

            writer.write( userName + "\n");
            writer.write( password + "\n");
            writer.write( name + "\n");
            writer.write( phone + "\n");
            writer.write( email + "\n");
            writer.write( balance + "\n");

            writer.write(currentJobID + "\n");


            for(String s: completedJobsID){
                writer.write( s + ",");
            }

            writer.write( "\n#\n");

            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Client searchClient(String userName) {
        Client client = null;
        try{
            BufferedReader reader = new BufferedReader(new FileReader("clients.txt"));

            String user;

            while((user = reader.readLine()) != null){
                String password , email, name, phone;
                double balance;

                password = reader.readLine();
                name = reader.readLine();
                phone = reader.readLine();
                email = reader.readLine();
                balance = Double.parseDouble(reader.readLine());

                String[] offeredJobs2 = reader.readLine().split(",");
                ArrayList<String> offeredJobs = new ArrayList<>(Arrays.asList(offeredJobs2));
                if(offeredJobs.getFirst().isEmpty()) offeredJobs.removeFirst();

                reader.readLine();

                if(user.equals(userName)){
                    client = new Client(name, phone, email, user,password,balance, offeredJobs, false);
                    break;
                }
            }

            reader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return client;
    }

    public static ServiceProvider searchProvider(String provider_userName) {
        ServiceProvider provider = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader("providers.txt"));

            String user_name;

            while((user_name = reader.readLine()) != null){

                String name, phone, email, password, currentJobID;
                double balance;
                ArrayList<String> completedJobs;

                password = reader.readLine();
                name = reader.readLine();
                phone = reader.readLine();
                email = reader.readLine();
                balance = Double.parseDouble(reader.readLine());
                currentJobID = reader.readLine();
                String[] completedJobs2 = reader.readLine().split(",");
                completedJobs = new ArrayList<>(Arrays.asList(completedJobs2));
                if(completedJobs.getFirst().isEmpty()) completedJobs.removeFirst();
                reader.readLine();

                if(user_name.equals(provider_userName)){
                    provider = new ServiceProvider(name, phone, email, user_name, password, balance,currentJobID,completedJobs ,false);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return provider;
    }

    public static ArrayList<Client> getAllClients(){
        ArrayList<Client> clients = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("clients.txt"));

            String user;

            while((user = reader.readLine()) != null){
                String password , email, name, phone;
                double balance;

                password = reader.readLine();
                name = reader.readLine();
                phone = reader.readLine();
                email = reader.readLine();
                balance = Double.parseDouble(reader.readLine());

                String[] offeredJobs2 = reader.readLine().split(",");
                ArrayList<String> offeredJobs = new ArrayList<>(Arrays.asList(offeredJobs2));
                if(offeredJobs.getFirst().isEmpty()) offeredJobs.removeFirst();

                reader.readLine();

                clients.add(new Client(name, phone, email, user, password,balance, offeredJobs, false));
            }

            reader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return clients;
    }
    public static ArrayList<ServiceProvider> getAllProviders(){

        ArrayList<ServiceProvider> providers = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("providers.txt"));

            String user_name;

            while((user_name = reader.readLine()) != null){

                String name, phone, email, password, currentJobID;
                double balance;
                ArrayList<String> completedJobs;

                password = reader.readLine();
                name = reader.readLine();
                phone = reader.readLine();
                email = reader.readLine();
                balance = Double.parseDouble(reader.readLine());
                currentJobID = reader.readLine();
                String[] completedJobs2 = reader.readLine().split(",");
                completedJobs = new ArrayList<>(Arrays.asList(completedJobs2));
                if(completedJobs.getFirst().isEmpty()) completedJobs.removeFirst();
                reader.readLine();

                providers.add(new ServiceProvider(name, phone, email, user_name, password, balance,currentJobID, completedJobs, false));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return providers;
    }
    public static ArrayList<Service> getAllServices(){

        ArrayList<Service> services = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("services.txt"));

            String ID;

            while((ID = reader.readLine()) != null){

                String title , description, status, provider_userName, client_userName;
                double price;
                ArrayList<String> applicants;

                title = reader.readLine();
                description = reader.readLine();
                status = reader.readLine();
                provider_userName = reader.readLine();
                client_userName = reader.readLine();
                price = Double.parseDouble(reader.readLine());

                String[] applicants2 = reader.readLine().split(",");
                applicants = new ArrayList<>(Arrays.asList(applicants2));
                if(applicants.getFirst().isEmpty()) applicants.removeFirst();

                reader.readLine();

                services.add(new Service(ID, title, description, client_userName,price, applicants, status, provider_userName, false));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return services;
    }

    public static void updateClients(Client updatedClient){
        ArrayList<Client> clients = getAllClients();

        for (int i = 0; i < clients.size(); i++) {
            if(clients.get(i).getUser_name().equals(updatedClient.getUser_name())){
                clients.set(i, updatedClient);
            }
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt"));
            writer.write("");

            writer.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        for(Client c : clients){
            insertClient(c.getUser_name(), c.getPassword(), c.getName(), c.getPhone(), c.getEmail(), c.getBalance(), c.getOfferedJobsID());
        }

    }
    public static void updateService(Service updatedService) {
        ArrayList<Service> services = getAllServices();

        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getID().equals(updatedService.getID())) {
                services.set(i, updatedService);
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("services.txt"));
            writer.write("");

            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for(Service s : services){
            insertService(s.getID(), s.getTitle(), s.getDescription(), s.getStatus(), s.getProvider_userName(), s.getClient_userName(), s.getPrice(), s.getApplicants_userName());
        }
    }
     public static void updateProvider(ServiceProvider updatedProvider){
            ArrayList<ServiceProvider> providers = getAllProviders();

            for (int i = 0; i < providers.size(); i++) {
                if(providers.get(i).getUser_name().equals(updatedProvider.getUser_name())){
                    providers.set(i, updatedProvider);
                }
            }

            try{

                BufferedWriter writer = new BufferedWriter(new FileWriter("providers.txt"));

                writer.write("");
                writer.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            for(ServiceProvider p : providers){
                insertProvider(p.getUser_name(), p.getPassword(), p.getName(), p.getPhone(), p.getEmail(), p.getBalance(), p.getCurrentJobID(), p.getCompletedJobsID());
            }
    }

    public static void deleteService(Service service){
        ArrayList<Service> services = getAllServices();
        Service deletingService = null;

        for(Service s: services){
            if(s.getID().equals(service.getID())){
                deletingService = s;
            }
        }

        if(deletingService != null)
            services.remove(deletingService);

        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter("services.txt"));

            writer.write("");
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for(Service s: services){
            insertService(s.getID(), s.getTitle(), s.getDescription(), s.getStatus(), s.getProvider_userName(), s.getClient_userName(), s.getPrice(), s.getApplicants_userName());
        }

    }
}
