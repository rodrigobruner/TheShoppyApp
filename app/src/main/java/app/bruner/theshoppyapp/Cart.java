package app.bruner.theshoppyapp;

public class Cart {

    private String name;

    private String province;

    private String kindOfComputer;

    private String brand;

    private boolean addSsd;

    private boolean addPrinter;

    private String extra;

    // Auto generated set and get to make it possible to add and return attribute values while keeping the encapsulation.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getKindOfComputer() {
        return kindOfComputer;
    }

    public void setKindOfComputer(String kindOfComputer) {
        this.kindOfComputer = kindOfComputer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isAddSsd() {
        return addSsd;
    }

    public void setAddSsd(boolean addSsd) {
        this.addSsd = addSsd;
    }

    public boolean isAddPrinter() {
        return addPrinter;
    }

    public void setAddPrinter(boolean addPrinter) {
        this.addPrinter = addPrinter;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    public String returnCart(MainActivity context) {

        //If print or SSD is select, the item is concatenated in additional
        String additional = "";
        if (isAddPrinter()) {
            additional += context.getString(R.string.field_additional_printer)+";";
        }

        if (isAddSsd()) {
            additional += context.getString(R.string.field_additional_ssd)+";";
        }

        // To saparate the calc and view I create the method getCost that calculate the cost
        float coast =  getCost(context);

        // Create a string to be displayed
        String cart = "Customer:" + this.name + "\n" +
                "Provincia:" + this.province + "\n" +
                "Computer:" + this.kindOfComputer + "\n" +
                "Brand:" + this.brand + "\n" +
                "Additional:"  + additional + "\n" +
                "Added Peripherals:" + this.extra + "\n" +
                "Cost: $"+ String.format("%.2f", coast);

        return cart; //Return formated string
    }

    private float getCost(MainActivity context){

        //Start coast as zero
        float cost = 0;

        // If is laptop
        if(this.kindOfComputer == context.getString(R.string.field_type_laptop)){

            //Add coast by brand
            switch (this.brand) {
                case "Dell":
                    cost += 1249;
                    break;
                case "HP":
                    cost += 1150;
                    break;
                case "Lenovo":
                    cost += 1549;
                    break;
                default:
                    cost += 0;
                    break;
            }
        } else { //If is desktop
            //Add cost by
            switch (this.brand) {
                case "Dell":
                    cost += 475;
                    break;
                case "HP":
                    cost += 400;
                    break;
                case "Lenovo":
                    cost += 450;
                    break;
                default:
                    cost += 0;
                    break;
            }
        }

        //Id SSD is selected
        if(this.isAddSsd()){
            cost += 60;
        }

        //Id Print is selected
        if(this.isAddPrinter()){
            cost += 100;
        }

        //Id Cooling is selected
        if(context.getString(R.string.field_extra_laptop_cooling).equals(this.extra)){
            cost += 33;
        }

        //Id USB Hub is selected
        if(context.getString(R.string.field_extra_laptop_hub).equals(this.extra)){
            cost += 60;
        }

        //Id laptop stand is selected
        if(context.getString(R.string.field_extra_laptop_stand).equals(this.extra)){
            cost += 139;
        }

        //Id webcan is selected
        if(context.getString(R.string.field_extra_desktop_webcam).equals(this.extra)){
            cost += 95;
        }

        //Id External HD is selected
        if(context.getString(R.string.field_extra_desktop_ehd).equals(this.extra)){
            cost += 64;
        }

        //Return cost + TAX 13%
        return (cost * 1.13f);
    }
}
