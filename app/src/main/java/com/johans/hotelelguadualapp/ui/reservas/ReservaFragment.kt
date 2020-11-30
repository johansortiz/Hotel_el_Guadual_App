package com.johans.hotelelguadualapp.ui.reservas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.ReservaServer
import com.johans.hotelelguadualapp.server.usuarioserver
import com.johans.hotelelguadualapp.ui.main_cliente.Main_Cliente_Activity
import kotlinx.android.synthetic.main.fragment_reserva.*
import java.text.SimpleDateFormat
import java.util.*

class ReservaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var fechaIngreso: String = ""
    private var fechaSalida: String = ""
    private var calingreso = Calendar.getInstance()
    private var calsalida = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: ReservaFragmentArgs by navArgs()
        val habitaciondetalle = args.HabitacionReserva
        id_habitacion_text.text = habitaciondetalle.id.toString()

        val database = FirebaseDatabase.getInstance()
        val myUsuarioRef = database.getReference("usuarios")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val uid = auth.currentUser?.uid
                    val Usuarioserver = data.getValue(usuarioserver::class.java)
                    if (Usuarioserver?.id.equals(uid)) {
                        id_cliente_text.text = uid
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myUsuarioRef.addListenerForSingleValueEvent(postListener)


        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfmonth ->
                calingreso.set(Calendar.YEAR, year)
                calingreso.set(Calendar.MONTH, month)
                calingreso.set(Calendar.DAY_OF_MONTH, dayOfmonth)

                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                fechaIngreso = sdf.format(calingreso.time).toString()
                btn_show_ingreso.text = fechaIngreso
            }

        val dateSetListener2 =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfmonth ->
                calsalida.set(Calendar.YEAR, year)
                calsalida.set(Calendar.MONTH, month)
                calsalida.set(Calendar.DAY_OF_MONTH, dayOfmonth)

                val format = "MM/dd/yyyy"
                val sdf2 = SimpleDateFormat(format, Locale.US)

                fechaSalida = sdf2.format(calsalida.time).toString()
                btn_show_salida.text = fechaSalida
            }
        btn_show_ingreso.setOnClickListener {
            context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    dateSetListener,
                    calingreso.get(Calendar.YEAR),
                    calingreso.get(Calendar.MONTH),
                    calingreso.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        btn_show_salida.setOnClickListener {
            context?.let { it2 ->
                DatePickerDialog(
                    it2,
                    dateSetListener2,
                    calsalida.get(Calendar.YEAR),
                    calsalida.get(Calendar.MONTH),
                    calsalida.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        btn_reserva.setOnClickListener {
            val id_cliente = id_cliente_text.text
            val id_habitacion = id_habitacion_text.text
            val fecha_ingreso = btn_show_ingreso.text.toString()
            val fecha_salida = btn_show_salida.text.toString()

            reservaEnFirebase(
                id_cliente.toString(),
                id_habitacion.toString().toInt(),
                fecha_ingreso,
                fecha_salida
            )
            Toast.makeText(
                context, "Reserva Efectuada con éxito",
                Toast.LENGTH_SHORT
            ).show()
            gotomenu()
        }
    }

    private fun gotomenu() {
        val intent = Intent(activity, Main_Cliente_Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun reservaEnFirebase(
        idCliente: String,
        idhabitacion: Int,
        fechaIngreso: String,
        fechasalida: String
    ) {

        val database = FirebaseDatabase.getInstance()
        val myReservaReference = database.getReference("reservas")

        val id = myReservaReference.push().key
        val reservaserver = ReservaServer(id, idCliente, idhabitacion, fechaIngreso, fechasalida)
        id?.let { myReservaReference.child(id).setValue(reservaserver) }

    }
}