package com.example.materialdesign1

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Variables para accesar a los views
        val control = findViewById<EditText>(R.id.txtControl)
        val nombre = findViewById<EditText>(R.id.txtNombre)
        val carrera = findViewById<EditText>(R.id.txtCarrera)
        //Botones
        val botonRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val botonEliminar = findViewById<Button>(R.id.btnEliminar)
        val botonModificar = findViewById<Button>(R.id.btnModificar)
        val botonConsultar = findViewById<Button>(R.id.btnConsultar)

        botonRegistrar.setOnClickListener {
            val objDBHelper = DBHelper(this, "agenda", null, 1)
            //Abrir la base de datos para escritura y lectura
            val bd = objDBHelper.writableDatabase
            //crear un objeto para llenarlo de datos (Campos)
            val registro = ContentValues()
            registro.put("control", Integer.parseInt(control.text.toString()))
            registro.put("nombre", nombre.text.toString())
            registro.put("carrera", carrera.text.toString())
            //Registrar los datos (registro)
            bd.insert("estudiantes", null, registro)
            //Cerrar la base de datos
            bd.close()
            control.setText("")
            nombre.setText("")
            carrera.setText("")
            control.requestFocus()
            Toast.makeText(this, "Registro Almacenado", Toast.LENGTH_LONG).show()
        }

        botonConsultar.setOnClickListener {
            val objDBHelper = DBHelper(this, "agenda", null, 1)
            //Abrir la base de datos para escritura y lectura
            val bd = objDBHelper.writableDatabase
            // reg es un objeto tipo arreglo que me permite almacenar los datos de la consulta
            val reg = bd.rawQuery("SELECT nombre, carrera from estudiantes where control=${Integer.parseInt(control.text.toString())}", null)
            if (reg.moveToFirst()) {
                nombre.setText(reg.getString(0))
                carrera.setText(reg.getString(1))
            } else {
                Toast.makeText(this, "El registro no existe", Toast.LENGTH_LONG).show()
            }
            bd.close();
        }
        botonEliminar.setOnClickListener {
            val objDBHelper = DBHelper(this, "agenda", null, 1)
            //Abrir la base de datos para escritura y lectura
            val bd = objDBHelper.writableDatabase
            //crear un objeto para llenarlo de datos (Campos)
            bd.execSQL("DELETE FROM estudiantes WHERE control=${Integer.parseInt(control.text.toString())}");
            //cerrar la base de datos
            bd.close();
            Toast.makeText(this, "El registro se ha eliminado", Toast.LENGTH_LONG).show()
        }

        }

    }




