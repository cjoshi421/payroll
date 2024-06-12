use ppc;

CREATE TABLE organization (
    org_id INT AUTO_INCREMENT PRIMARY KEY,
    organization_name VARCHAR(255) NOT NULL
);

CREATE TABLE employee_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    designation VARCHAR(255),
    org_id INT,
    FOREIGN KEY (org_id) REFERENCES organization(org_id)
);

ALTER TABLE `ppc`.`employee_details` 
ADD UNIQUE INDEX `emp_id` (`emp_id` ASC) VISIBLE;
;


CREATE TABLE event (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    value DECIMAL(10, 2),
    notes TEXT,
    id int,
    FOREIGN KEY (id) REFERENCES employee_details(id)
);

