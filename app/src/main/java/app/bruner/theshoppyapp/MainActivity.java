package app.bruner.theshoppyapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Tag for debug on Catlog
    private static String TAG = "MainActivity";

    //LAYOUTS
    LinearLayout formLayout;
    LinearLayout cartLayout;

    //NAME
    //Field Name
    EditText editTextName;

    //PROVINCES
    //List of provinces
    String[] provinces;

    //Field Province
    AutoCompleteTextView provinceTextView;


    //KIND OF COMPUTER
    //Radio group of kinds of computer
    RadioGroup radioGroupKindOfComputer;

    //String to save the kind of computer selected
    String kindOfComputer;

    //Radio fo the option desktop
    RadioButton radioButtonDesktop;

    //Radio for the option laptop
    RadioButton radioButtonLaptop;

    //BRAND
    // Array with Brands
    String[] brandList = {"Dell", "HP", "Lenovo"};

    //String to save the brand selected
    String selectedBrand = "Dell";


    //ADITIONAL
    //Chackbox for the option SSD
    CheckBox checkBoxSSD;

    //Checkbox for the option printer
    CheckBox checkBoxPrinter;

    //PERIPHEREALS
    //Radio group with laptop options
    RadioGroup radioGroupLaptop;

    //String to save the option selected in this group
    String selectedPeripheralsLaptop;

    //Raio group with desktop options
    RadioGroup radioGroupDesktop;

    //String to save the option selected in this group
    String selectedPeripheralsDesktop;

    //Button
    Button buttonAddToCart;

    //editText to show the cart
    EditText editTextTextMultiLine;

    //Error control
    boolean noError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_layout);

        //set the layouts
        formLayout = findViewById(R.id.formLayout);
        cartLayout = findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.GONE); //Hide this layout when start

        // Set a list of provinces into provinces array
        provinces = Provinces.getProvinces();

        // Set user name field
        editTextName = findViewById(R.id.editTextName);

        // Province Auto Complete Text View
        //Set AutoCompleteTextView
        provinceTextView = findViewById(R.id.autoCompleteTextViewProvince);
        //Creating an adapter for the AutoCompletetextView
        ArrayAdapter<String> provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, provinces);
        //Set the adapter for AutoCompletetextView
        provinceTextView.setAdapter(provincesAdapter);

        //Kind of computer; set the elements
        radioGroupKindOfComputer = findViewById(R.id.radioGroupKindOfComputer);
        radioButtonDesktop = findViewById(R.id.radioButtonDesktop);
        radioButtonLaptop = findViewById(R.id.radioButtonLaptop);

        //Brands
        //Set Spinner
        Spinner brands = findViewById(R.id.spinnerBrands);
        //Creating an adapter for the Spinner
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, brandList);
        //Set the adapter for Spinner
        brands.setAdapter(brandAdapter);
        //Add a listener, when the value change set selectedBrand with the text
        brands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBrand = brandList[i];
                Log.d(TAG, "Selected brand[Set]: " + selectedBrand);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Aditional; Set the elements
        checkBoxSSD = findViewById(R.id.checkBoxSSD);
        checkBoxPrinter = findViewById(R.id.checkBoxPrinter);

        // Extra peripherals
        radioGroupLaptop = findViewById(R.id.radioGroupLaptop);
        radioGroupLaptop.setVisibility(View.GONE); //Hide this group

        radioGroupDesktop = findViewById(R.id.radioGroupDesktop);

        //By default desktop is selected
        kindOfComputer = getString(R.string.field_type_desktop);
        //Add a listener in Kind of computer, when change...
        radioGroupKindOfComputer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton selectedRadionButton = findViewById(id); // Get field selected
                String laptop = getString(R.string.field_type_laptop); // Get String laptop
                //If the selected field is laptop
                if(selectedRadionButton.getText() == laptop){
                    kindOfComputer = laptop; //Set kindOfComputer attribute as laptop
                    radioGroupLaptop.setVisibility(View.VISIBLE); //Show laptop options
                    radioGroupDesktop.setVisibility(View.GONE); // Hide desktop options
                } else {
                    radioGroupDesktop.setVisibility(View.VISIBLE); // Show desktop options
                    radioGroupLaptop.setVisibility(View.GONE); //Hide Laptop options
                    // Set kindOfComputer attribute as Desktop
                    kindOfComputer = getString(R.string.field_type_desktop);
                }
            }
        });


        //Add a listener for a group of laptop options
        radioGroupLaptop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override //On change selected radio into radio group
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton selectedRadionButton = findViewById(id); //Get option selected
                //Set selectedPeripheralsLaptop attribute with the text of the option selected
                selectedPeripheralsLaptop = selectedRadionButton.getText().toString();
            }
        });

        //The same logic applied for the laptop options, but for the desktop options
        radioGroupDesktop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton selectedRadionButton = findViewById(id);
                selectedPeripheralsDesktop = selectedRadionButton.getText().toString();
            }
        });

        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
    }

    //When add to cart button is pressed this function is called
    public void onAddToCartButtonClick(View view){

        //New object cart is create
        Cart cart = new Cart();

        //Set as no error
        noError = true;
        //If name length less than 2 characters shows error
        if(editTextName.getText().toString().length() < 2){
            //Display a message on the screen with the error message.
            Toast.makeText(MainActivity.this, getString(R.string.error_name), Toast.LENGTH_LONG).show();
            noError = false; //Ops, report the error
        }

        //If the province is not in array of valid province or territories show error
        if(!Arrays.asList(provinces).contains(provinceTextView.getText().toString())){
            Toast.makeText(MainActivity.this, getString(R.string.error_province), Toast.LENGTH_LONG).show();
            noError = false; //report the error
        }

        //Set attributes
        cart.setName(editTextName.getText().toString());
        cart.setProvince(provinceTextView.getText().toString());
        cart.setKindOfComputer(kindOfComputer);
        Log.d(TAG, "Selected brand[Get]: " + selectedBrand);
        cart.setBrand(selectedBrand);

        //Set Printer
        cart.setAddPrinter(false); //Start as false
        if(checkBoxPrinter.isChecked()){ //if is checked turn to true
            cart.setAddPrinter(true);
        }

        //Set SSD
        cart.setAddSsd(false); //Start as false
        if(checkBoxSSD.isChecked()){ //if is checked turn to true
            cart.setAddSsd(true);
        }

        //By default is set with desktop option
        cart.setExtra(selectedPeripheralsDesktop);
        //If laptop was selected set laptop option
        if(kindOfComputer.equals(getString(R.string.field_type_laptop))){
            cart.setExtra(selectedPeripheralsLaptop);
        }

        //Show the string with shopping cart info
        editTextTextMultiLine.setText(cart.returnCart(this));

        if(noError){ // just if no errors, show shopping cart info
            formLayout.setVisibility(View.GONE); //Hide form layout
            cartLayout.setVisibility(View.VISIBLE); //Show cart layout
        }
    }

    //When back button is pressed this function is called
    public void onBackButtonClick(View view){
        formLayout.setVisibility(View.VISIBLE);
        cartLayout.setVisibility(View.GONE);
    }
}