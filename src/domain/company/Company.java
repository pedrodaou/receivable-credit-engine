package domain.company;

import domain.shared.DomainException;

public class Company {
    private final String id;
    private final String name;
    private final String cnpj;

    public Company(String id, String name, String cnpj){
        if (id == null || id.isBlank()){
            throw new DomainException("Company id is required and must not be null or blank.");
        }
        if (name == null || name.isBlank()){
            throw new DomainException("Company name is required and must not be null or blank.");
        }
        if (cnpj == null || cnpj.isBlank()){
            throw new DomainException("Company cnpj is required and must not be null or blank.");
        }

        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCnpj(){
        return cnpj;
    }
}
