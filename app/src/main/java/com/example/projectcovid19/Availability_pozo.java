package com.example.projectcovid19;

public class Availability_pozo {

    private  String icubeds;
    private String nonicubeds;
    private String Vaccines;
    private String drugs;
    private String OxygenBeds;
    private String O2concentration;

    public Availability_pozo(String icubeds, String nonicubeds, String vaccines, String drugs, String oxygenBeds, String o2concentration) {
        this.icubeds = icubeds;
        this.nonicubeds = nonicubeds;
        this.Vaccines = vaccines;
        this.drugs = drugs;
       this.OxygenBeds = oxygenBeds;
        this.O2concentration = o2concentration;
    }

    public String getIcubeds() {
        return icubeds;
    }

    public void setIcubeds(String icubeds) {
        this.icubeds = icubeds;
    }

    public String getNonicubeds() {
        return nonicubeds;
    }

    public void setNonicubeds(String nonicubeds) {
        this.nonicubeds = nonicubeds;
    }

    public String getVaccines() {
        return Vaccines;
    }

    public void setVaccines(String vaccines) {
        Vaccines = vaccines;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getOxygenBeds() {
        return OxygenBeds;
    }

    public void setOxygenBeds(String oxygenBeds) {
        OxygenBeds = oxygenBeds;
    }

    public String getO2concentration() {
        return O2concentration;
    }

    public void setO2concentration(String o2concentration) {
        O2concentration = o2concentration;
    }
}
