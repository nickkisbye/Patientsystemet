package dk.patientsystemet.demo.Service;

import dk.patientsystemet.demo.Model.Consultation;
import dk.patientsystemet.demo.Repositories.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    ConsultationRepository db;

    @Autowired
    Validate val;

    public List<Consultation> getConsultations(int id) throws SQLException {
        ResultSet rs = db.findConsultations(id);
        List<Consultation> consultationList = new ArrayList<>();
        while (rs.next()) {
            Consultation consultation = new Consultation();
            consultation.setPatientId(rs.getInt("patient.id"));
            consultation.setId(rs.getInt("consultation.id"));
            consultation.setDescription(rs.getString("consultation.description"));
            consultation.setDate(rs.getString("consultation.date"));
            consultationList.add(consultation);
        }
        return consultationList;
    }

    public List<Consultation> findDoctorsConsultationsById(int id) throws SQLException {
        ResultSet rs = db.findDoctorsConsultationById(id);
        List<Consultation> consultationList = new ArrayList<>();
        while (rs.next()) {
            Consultation consultation = new Consultation();
            consultation.setPatientId(rs.getInt("patient.id"));
            consultation.setId(rs.getInt("consultation.id"));
            consultation.setDescription(rs.getString("consultation.description"));
            consultation.setDate(rs.getString("consultation.date"));
            consultationList.add(consultation);
        }
        return consultationList;
    }

    public String deleteConsultation(int id) throws SQLException {
        db.deleteConsultation(id);
        return "Success";
    }

    public String createConsultation(Consultation consultation) throws SQLException {
        if(val.biggerOrEqualToNumber(consultation.getDescription(), 1)) {
            return "Fill out description";
        } else if(val.biggerOrEqualToNumber(consultation.getConclusion(), 1)) {
            return "Fill out conclusion";
        } else {
            db.createConsultation(consultation);
            return "Success";
        }

    }
    public String editConsultation(Consultation consultation) throws SQLException {
        if(val.biggerOrEqualToNumber(consultation.getDescription(), 1)) {
            return "Fill out description";
        } else if(val.biggerOrEqualToNumber(consultation.getConclusion(), 1)) {
            return "Fill out conclusion";
        } else {
            db.editConsultation(consultation);
            return "Success";
        }
    }
    public List<Consultation> upcomingConsultations(int userId) throws SQLException {
        ResultSet rs = db.upcomingConsultations(userId);
        List<Consultation> ucList = new ArrayList<>();
        while (rs.next()) {
            Consultation consultation = new Consultation();
            consultation.setId(rs.getInt("consultation.id"));
            consultation.setPatientName(rs.getString("patient.firstname"));
            consultation.setDate(rs.getString("consultation.date"));
            ucList.add(consultation);
        }
        return ucList;
    }

    public Consultation findConsultationById(int id) throws SQLException {
        ResultSet rs = db.findConsultationById(id);
        if (rs.next()) {
            Consultation consultation = new Consultation();
            consultation.setPatientId(rs.getInt("patient.id"));
            consultation.setPatientName(rs.getString("patient.firstname") + " " + rs.getString("patient.lastname"));
            consultation.setId(rs.getInt("consultation.id"));
            consultation.setConclusion(rs.getString("consultation.conclusion"));
            consultation.setDescription(rs.getString("consultation.description"));
            consultation.setDate(rs.getString("consultation.date"));
            return consultation;
        }

        return null;
    }

}
