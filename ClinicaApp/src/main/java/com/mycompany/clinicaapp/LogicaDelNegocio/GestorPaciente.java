package com.mycompany.clinicaapp.LogicaDelNegocio;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import com.mycompany.clinicaapp.Interfaces.IRepositorioPaciente;
import com.mycompany.clinicaapp.Persistencia.RepositorioPaciente;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa la interfaz IPacienteService y se encarga de gestionar las operaciones CRUD sobre los objetos Paciente
 * @author Valentina
 */

public class GestorPaciente implements IPacienteService{

    private final ArrayList<Paciente> listaPacientes;
    private final IRepositorioPaciente repositorioPaciente;

    /**
     * Constructor por defecto 
     */

    public GestorPaciente() {
        this.repositorioPaciente = new RepositorioPaciente();
        List<Paciente> pacientesCargados = repositorioPaciente.cargar();
        this.listaPacientes = new ArrayList<>(pacientesCargados != null ? pacientesCargados : new ArrayList<>());

        // Si la lista está vacía (primera vez, se crean pacientes por defecto)
        if (listaPacientes.isEmpty()){
            listaPacientes.add(new Paciente("1098453951", "Eliza", "3185249971", 21, "1111"));
            listaPacientes.add(new Paciente("1096607451", "Carlos", "3182749841", 30, "2222"));

            repositorioPaciente.guardar(listaPacientes);
        }
    }

    /**
     * Este método registra a un nuevo paciente
     * @param paciente  Paciente nuevo
     * @return true  (En caso de que el paciente sea creado con éxito)
     */
    @Override
    public boolean registrarPaciente(Paciente paciente) {
        // en caso de que este vacío
        if (paciente == null) {
            return false;
        }

        for (Paciente pac : listaPacientes) {
            if (pac.getCedula().equals(paciente.getCedula())) {
                return false; // si ya existe un paciente con esa cédula
            }
        }
        
        listaPacientes.add(paciente);
        repositorioPaciente.guardar(listaPacientes);
        return true;
    }

    /**
     * Este método edita los datos de un paciente
     * @param paciente  Paciente a editar
     * @return true  (En caso de que el paciente sea encontrado y editado con éxito)
     */
    @Override
    public boolean editarPaciente(Paciente paciente) {
        for (int i=0; i < listaPacientes.size(); i++){
            if (paciente.getCedula().equals(listaPacientes.get(i).getCedula())){
                listaPacientes.set(i, paciente);
                repositorioPaciente.guardar(listaPacientes);
                return true;
            }
        }
        return false;
    }

    /**
     * Este método elimina a un paciente
     * @param paciente  Paciente a eliminar
     * @return true  (En caso de que el paciente sea eliminado con éxito)
     */
    @Override
    public boolean eliminarPaciente(Paciente paciente) {
        for (int i=0; i < listaPacientes.size(); i++){
            if (paciente.getCedula().equals(listaPacientes.get(i).getCedula())) {
                listaPacientes.remove(i);
                repositorioPaciente.guardar(listaPacientes);
                return true;
            }
        }
        return false;
    }

    /**
     * Este método lista a los pacientes registrados
     * @return listaPacientes (una copia de la lista de pacientes registrados)
     */
    @Override
    public List<Paciente> listarPacientes() {
        return new ArrayList<>(listaPacientes);
    }

    /**
     * Este método busca a un paciente por medio de su cédula
     * @param cedula cédula del paciente
     * @return p (el paciente. En caso de que no se encuentre, retorna null)
     */
    @Override
    public Paciente buscarPorCedulaPaciente(String cedula) {
        for (Paciente p : listaPacientes) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }
}