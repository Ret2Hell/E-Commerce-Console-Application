class CustomerDetails {
    private String fullName;
    private String country;
    private String region;
    private String town;
    private String zipCode;
    private String address;
    private String phoneNumber;

    public CustomerDetails(String fullName,String country,String region,String town,String zipCode,String address,String phoneNumber) {
        this.fullName=fullName;
        this.country=country;
        this.region=region;
        this.town=town;
        this.zipCode=zipCode;
        this.address=address;
        this.phoneNumber=phoneNumber;
    }

    @Override
    public String toString() {
        return "FullName: " + fullName + ", Address: " + address + ", Phone: " + phoneNumber;
    }
}