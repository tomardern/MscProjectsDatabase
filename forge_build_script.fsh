clear;

set ACCEPT_DEFAULTS true;

new-project --named "MscProjectsDatbase" --topLevelPackage com.tom.mscprojectsdatabase --projectFolder "E:\Websites\webenterprise\MscProjectsDatabase"

scaffold setup --scaffoldType faces;
persistence setup --provider ECLIPSELINK --container GLASSFISH_3;

validation setup;



@/* Organisation Entity ---------------------- */;
entity --named Organisation;

field string --named name;			constraint NotNull --onProperty name;
field string --named username;		constraint NotNull --onProperty username;
field string --named password;		constraint NotNull --onProperty password;
field string --named email;			constraint NotNull --onProperty email;
field string --named telephone;		constraint NotNull --onProperty telephone;
field string --named fax;


field string --named orgname;		constraint NotNull --onProperty orgname;
field string --named drescription;
field string --named address;
field string --named website;
field boolean --named verified;

field temporal --type DATE --named added;


@/* Project Entity ---------------------- */;
entity --named Project;

field string --named name;				constraint NotNull --onProperty name;
field string --named question;			constraint NotNull --onProperty question;
field string --named description;		constraint NotNull --onProperty description;

field temporal --type DATE --named deadline;		constraint NotNull --onProperty deadline;
field temporal --type DATE --named added;

field boolean --named approved;
field string --named notes;


@/* Deliverable ---------------------- */;
entity --named Deliverable;

field string --named name;				constraint NotNull --onProperty name;
field temporal --type DATE --named deadline;	constraint NotNull --onProperty deadline;

cd ../;

@/* Relationships ---------------------- */;

cd Deliverable.java;
field manyToOne --named project --fieldType ~.model.Project.java --inverseFieldName deliverables;
cd ../;

cd Project.java;
field manyToOne --named organisation --fieldType ~.model.Organisation.java --inverseFieldName projects;

cd ../

@/* Generate the UI for all of our @Entities at once */;
scaffold setup;
scaffold from-entity ~.model.* --scaffoldType faces --overwrite;
cd ../


