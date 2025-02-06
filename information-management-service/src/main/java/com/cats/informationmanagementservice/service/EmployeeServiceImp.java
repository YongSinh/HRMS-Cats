package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.*;
import com.cats.informationmanagementservice.events.MessageFull;
import com.cats.informationmanagementservice.listener.KafKaProducerService;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final WebClient.Builder webClientBuilder;
    private final KafKaProducerService kafKaProducerService;

    @Transactional
    @Override
    public Employee addPersonalData(EmployeeDtoReq employee, MultipartFile file) throws IOException {
        if (employeeRepo.findByEmpId(employee.getEmpId()).isPresent()) {
            throw new IllegalArgumentException("An employee with this ID already exists.");
        }

        Employee emp = new Employee();
        emp.setEmpId(employee.getEmpId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setPhone(employee.getPhone());
        emp.setBirthDate(employee.getBirthDate());
        emp.setPlaceOfBirth(employee.getPlaceOfBirth());
        emp.setAge(employee.getAge());
        emp.setSex(employee.getSex());
        emp.setHeight(employee.getHeight());
        emp.setWeight(employee.getWeight());
        emp.setAddress(employee.getAddress());
        emp.setEmpDate(employee.getEmpDate());
        emp.setJoinDate(employee.getJoinDate());
        emp.setMangerId(employee.getMangerId());
        emp.setLocation(employee.getLocation());
        emp.setMaritalStats(employee.getMaritalStats());
        emp.setNationality(employee.getNationality());
        emp.setWorkType(employee.getWorkType());
        emp.setReligion(employee.getReligion());
        emp.setIdCard(employee.getIdCard());
        emp.setPassport(employee.getPassport());
        emp.setRemark(employee.getRemark());
        emp.setGovOfficer(employee.getGovOfficer());
        emp.setGovTel(employee.getGovTel());
        emp.setGovPosition(employee.getGovPosition());
        emp.setGovAddress(employee.getGovAddress());
        if (employee.getPosId() == null) {
            throw new IllegalArgumentException("Employee at least on Position ");
        }
        Position position = positionService.getPositionById(employee.getPosId());
        emp.setPosition(position);
        if (employee.getDepId() == null) {
            throw new IllegalArgumentException("Employee at least on Position ");
        }
        Department department = departmentService.getDepById(employee.getDepId());
        emp.setDepartment(department);
        if (file != null) {
            System.out.println(file.isEmpty());
            uploadFile(file, employee.getEmpId(), 1, employee.getEmpDate(), 1);
        }
        MessageFull messageFull = new MessageFull();
        messageFull.setSender("Human Resources");
        messageFull.setDateTime(LocalDateTime.now());
        messageFull.setEnglishText("Welcome, " + (employee.getSex().equalsIgnoreCase("male") ? "Mr. " : "Mrs. ")
                + employee.getLastName() + " " + employee.getFirstName() + " to CATS! We're excited to have you on board.");
        messageFull.setReceiver("All");
        kafKaProducerService.senGendMessage(messageFull);
        return employeeRepo.save(emp);
    }

    @Transactional
    @Override
    public Employee userUpdateInfo(EmployeeDtoReqEdit employee, MultipartFile file) throws IOException {
        Employee emp = getPersonalDataById(employee.getEmpId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setPhone(employee.getPhone());
        emp.setBirthDate(employee.getBirthDate());
        emp.setPlaceOfBirth(employee.getPlaceOfBirth());
        emp.setAge(employee.getAge());
        emp.setSex(employee.getSex());
        emp.setHeight(employee.getHeight());
        emp.setWeight(employee.getWeight());
        emp.setAddress(employee.getAddress());
        emp.setMaritalStats(employee.getMaritalStats());
        emp.setNationality(employee.getNationality());
        emp.setReligion(employee.getReligion());
        emp.setIdCard(employee.getIdCard());
        emp.setPassport(employee.getPassport());
        emp.setRemark(employee.getRemark());
        emp.setGovOfficer(employee.getGovOfficer());
        emp.setGovTel(employee.getGovTel());
        emp.setGovPosition(employee.getGovPosition());
        emp.setGovAddress(employee.getGovAddress());
        if (file != null) {
            if (employee.getFileId().isEmpty()) {
                uploadFile(file, employee.getEmpId(), 1, emp.getEmpDate(), 1);
            } else {
                uploadUpdateFile(file, employee.getEmpId(), 1, emp.getEmpDate(), 1, employee.getFileId());
            }

        }
        return employeeRepo.save(emp);
    }

    @Transactional
    @Override
    public EmployeeDtoRep editPersonalData(EmployeeDtoReqEdit employee, MultipartFile file) throws IOException {
        Employee emp = update(employee);
        if (file != null) {
            if (employee.getFileId().isEmpty()) {
                uploadFile(file, employee.getEmpId(), 1, employee.getEmpDate(), 1);
            } else {
                uploadUpdateFile(file, employee.getEmpId(), 1, employee.getEmpDate(), 1, employee.getFileId());
            }

        }
        return mapper.EmployeeDtoRepToEmployeeDtoRep(emp);
    }

    @Override
    public Employee update(EmployeeDtoReqEdit employee) {
        Employee emp = getPersonalDataById(employee.getEmpId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setPhone(employee.getPhone());
        emp.setBirthDate(employee.getBirthDate());
        emp.setPlaceOfBirth(employee.getPlaceOfBirth());
        emp.setAge(employee.getAge());
        emp.setSex(employee.getSex());
        emp.setHeight(employee.getHeight());
        emp.setWeight(employee.getWeight());
        emp.setAddress(employee.getAddress());
        emp.setEmpDate(employee.getEmpDate());
        emp.setJoinDate(employee.getJoinDate());
        emp.setMangerId(employee.getMangerId());
        emp.setLocation(employee.getLocation());
        emp.setMaritalStats(employee.getMaritalStats());
        emp.setNationality(employee.getNationality());
        emp.setWorkType(employee.getWorkType());
        emp.setReligion(employee.getReligion());
        emp.setIdCard(employee.getIdCard());
        emp.setPassport(employee.getPassport());
        emp.setRemark(employee.getRemark());
        emp.setGovOfficer(employee.getGovOfficer());
        emp.setGovTel(employee.getGovTel());
        emp.setGovPosition(employee.getGovPosition());
        emp.setGovAddress(employee.getGovAddress());
        if (employee.getPosId() != null) {
            Position position = positionService.getPositionById(employee.getPosId());
            emp.setPosition(position);
        }
        if (employee.getDepId() != null) {
            Department department = departmentService.getDepById(employee.getDepId());
            emp.setDepartment(department);
        }

        return employeeRepo.save(emp);
    }

    @Override
    public List<EmployeeDtoRep> listEmployee() {
        List<Employee> employees = StreamSupport
                .stream(employeeRepo.findAll().spliterator(), false)
                .toList();
        return mapper.EmployeeDtoRepToEmployeeDtoReps(employees);
    }

    @Override
    public EmployeeInfo getEmpInfoByEmId(Long emId) {
        return mapper.employeeToEmployeeInfoDto(getPersonalDataById(emId));
    }

    @Override
    public List<EmployeeDtoRep> getEmployeeByDep(Long depId) {
        Department department = departmentService.getDepById(depId);
        return mapper.EmployeeDtoRepToEmployeeDtoReps(employeeRepo.findByDepartment(department));
    }

    @Override
    public List<EmployeeDtoRep> getEmployeeByUnderManger(Long emId) {
        Employee getEmp = getPersonalDataById(emId);
        List<Employee> employeeList = employeeRepo.callGetEmployeeHierarchyProcedure(getEmp.getMangerId(), getEmp.getDepartment().getDepId());
        return mapper.EmployeeDtoRepToEmployeeDtoReps(employeeList);
    }

    @Override
    public List<EmployeeDtoRep> getEmployeeByDepAndPos(Long depId, String posId) {
        Position position = positionService.getPositionById(posId);
        Department department = departmentService.getDepById(depId);
        return mapper.EmployeeDtoRepToEmployeeDtoReps(employeeRepo.findByDepartmentAndPosition(department, position));
    }


    @Override
    public EmployeeDtoRep getEmployeeDtoRepById(Long Id) {
        return mapper.EmployeeDtoRepToEmployeeDtoRep(getPersonalDataById(Id));
    }

    @Override
    public List<Long> getEmployeeByDepGetOnlyEmId(Long depId) {
        Department department = departmentService.getDepById(depId);
        List<Employee> employees = employeeRepo.findByDepartment(department);
        List<Long> emId = new ArrayList<>();
        for (Employee employee : employees) {
            emId.add(employee.getEmpId());
        }
        return emId;
    }

    @Override
    public List<Long> getEmployeeByDepAndPosId(Long depId, String posId) {
        Position position = positionService.getPositionById(posId);
        Department department = departmentService.getDepById(depId);
        List<Employee> employees = employeeRepo.findByDepartmentAndPosition(department, position);
        List<Long> emId = new ArrayList<>();
        for (Employee employee : employees) {
            emId.add(employee.getEmpId());
        }
        return emId;
    }


    @Override
    public void uploadFile(MultipartFile file, Long emId, Integer type, LocalDate date, Integer serviceType) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("emId", emId);
        jsonBody.put("type", type);
        jsonBody.put("dateCreated", date);
        jsonBody.put("serviceType", serviceType);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());
        builder.part("body", jsonBody);
        webClientBuilder.build().post()
                .uri("http://attendance-service/api/files/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public void uploadUpdateFile(MultipartFile file, Long emId, Integer type, LocalDate date, Integer serviceType, String fileId) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("emId", emId);
        jsonBody.put("type", type);
        jsonBody.put("dateCreated", date);
        jsonBody.put("serviceType", serviceType);
        jsonBody.put("id", fileId);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());
        builder.part("body", jsonBody);
        webClientBuilder.build().post()
                .uri("http://attendance-service/api/files/updateFile")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public Employee getPersonalDataById(Long Id) {
        return employeeRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee with id: " + Id + " could not be found"));
    }

    @Override
    public void deleteEmpInfo(Long emId) {
        Employee employee = getPersonalDataById(emId);
        employeeRepo.delete(employee);

    }

    @Override
    public List<Long> getListEmpId() {
        return employeeRepo.findEmployeeId();
    }

    @Override
    public String getEmpFullName(Long emId) {
        return employeeRepo.getEmpFullName(emId);
    }
}

