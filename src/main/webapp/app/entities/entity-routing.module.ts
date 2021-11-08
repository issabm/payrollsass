import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employe',
        data: { pageTitle: 'payrollApp.employe.home.title' },
        loadChildren: () => import('./employe/employe.module').then(m => m.EmployeModule),
      },
      {
        path: 'palier-plate',
        data: { pageTitle: 'payrollApp.palierPlate.home.title' },
        loadChildren: () => import('./palier-plate/palier-plate.module').then(m => m.PalierPlateModule),
      },
      {
        path: 'palier-condition',
        data: { pageTitle: 'payrollApp.palierCondition.home.title' },
        loadChildren: () => import('./palier-condition/palier-condition.module').then(m => m.PalierConditionModule),
      },
      {
        path: 'conjoint',
        data: { pageTitle: 'payrollApp.conjoint.home.title' },
        loadChildren: () => import('./conjoint/conjoint.module').then(m => m.ConjointModule),
      },
      {
        path: 'enfant',
        data: { pageTitle: 'payrollApp.enfant.home.title' },
        loadChildren: () => import('./enfant/enfant.module').then(m => m.EnfantModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'payrollApp.contact.home.title' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'mode-input',
        data: { pageTitle: 'payrollApp.modeInput.home.title' },
        loadChildren: () => import('./mode-input/mode-input.module').then(m => m.ModeInputModule),
      },
      {
        path: 'frequence',
        data: { pageTitle: 'payrollApp.frequence.home.title' },
        loadChildren: () => import('./frequence/frequence.module').then(m => m.FrequenceModule),
      },
      {
        path: 'sens',
        data: { pageTitle: 'payrollApp.sens.home.title' },
        loadChildren: () => import('./sens/sens.module').then(m => m.SensModule),
      },
      {
        path: 'mode-calcul',
        data: { pageTitle: 'payrollApp.modeCalcul.home.title' },
        loadChildren: () => import('./mode-calcul/mode-calcul.module').then(m => m.ModeCalculModule),
      },
      {
        path: 'concerne',
        data: { pageTitle: 'payrollApp.concerne.home.title' },
        loadChildren: () => import('./concerne/concerne.module').then(m => m.ConcerneModule),
      },
      {
        path: 'type-contrat',
        data: { pageTitle: 'payrollApp.typeContrat.home.title' },
        loadChildren: () => import('./type-contrat/type-contrat.module').then(m => m.TypeContratModule),
      },
      {
        path: 'sous-type-contrat',
        data: { pageTitle: 'payrollApp.sousTypeContrat.home.title' },
        loadChildren: () => import('./sous-type-contrat/sous-type-contrat.module').then(m => m.SousTypeContratModule),
      },
      {
        path: 'niveau-scolaire',
        data: { pageTitle: 'payrollApp.niveauScolaire.home.title' },
        loadChildren: () => import('./niveau-scolaire/niveau-scolaire.module').then(m => m.NiveauScolaireModule),
      },
      {
        path: 'niveau-etude',
        data: { pageTitle: 'payrollApp.niveauEtude.home.title' },
        loadChildren: () => import('./niveau-etude/niveau-etude.module').then(m => m.NiveauEtudeModule),
      },
      {
        path: 'situation-familiale',
        data: { pageTitle: 'payrollApp.situationFamiliale.home.title' },
        loadChildren: () => import('./situation-familiale/situation-familiale.module').then(m => m.SituationFamilialeModule),
      },
      {
        path: 'type-identite',
        data: { pageTitle: 'payrollApp.typeIdentite.home.title' },
        loadChildren: () => import('./type-identite/type-identite.module').then(m => m.TypeIdentiteModule),
      },
      {
        path: 'identite',
        data: { pageTitle: 'payrollApp.identite.home.title' },
        loadChildren: () => import('./identite/identite.module').then(m => m.IdentiteModule),
      },
      {
        path: 'nature-adhesion',
        data: { pageTitle: 'payrollApp.natureAdhesion.home.title' },
        loadChildren: () => import('./nature-adhesion/nature-adhesion.module').then(m => m.NatureAdhesionModule),
      },
      {
        path: 'situation',
        data: { pageTitle: 'payrollApp.situation.home.title' },
        loadChildren: () => import('./situation/situation.module').then(m => m.SituationModule),
      },
      {
        path: 'sexe',
        data: { pageTitle: 'payrollApp.sexe.home.title' },
        loadChildren: () => import('./sexe/sexe.module').then(m => m.SexeModule),
      },
      {
        path: 'nature-absence',
        data: { pageTitle: 'payrollApp.natureAbsence.home.title' },
        loadChildren: () => import('./nature-absence/nature-absence.module').then(m => m.NatureAbsenceModule),
      },
      {
        path: 'solde-absence',
        data: { pageTitle: 'payrollApp.soldeAbsence.home.title' },
        loadChildren: () => import('./solde-absence/solde-absence.module').then(m => m.SoldeAbsenceModule),
      },
      {
        path: 'emploi',
        data: { pageTitle: 'payrollApp.emploi.home.title' },
        loadChildren: () => import('./emploi/emploi.module').then(m => m.EmploiModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'payrollApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'echlon',
        data: { pageTitle: 'payrollApp.echlon.home.title' },
        loadChildren: () => import('./echlon/echlon.module').then(m => m.EchlonModule),
      },
      {
        path: 'type-handicap',
        data: { pageTitle: 'payrollApp.typeHandicap.home.title' },
        loadChildren: () => import('./type-handicap/type-handicap.module').then(m => m.TypeHandicapModule),
      },
      {
        path: 'groupe',
        data: { pageTitle: 'payrollApp.groupe.home.title' },
        loadChildren: () => import('./groupe/groupe.module').then(m => m.GroupeModule),
      },
      {
        path: 'entreprise',
        data: { pageTitle: 'payrollApp.entreprise.home.title' },
        loadChildren: () => import('./entreprise/entreprise.module').then(m => m.EntrepriseModule),
      },
      {
        path: 'affiliation',
        data: { pageTitle: 'payrollApp.affiliation.home.title' },
        loadChildren: () => import('./affiliation/affiliation.module').then(m => m.AffiliationModule),
      },
      {
        path: 'devise',
        data: { pageTitle: 'payrollApp.devise.home.title' },
        loadChildren: () => import('./devise/devise.module').then(m => m.DeviseModule),
      },
      {
        path: 'carriere',
        data: { pageTitle: 'payrollApp.carriere.home.title' },
        loadChildren: () => import('./carriere/carriere.module').then(m => m.CarriereModule),
      },
      {
        path: 'contrat',
        data: { pageTitle: 'payrollApp.contrat.home.title' },
        loadChildren: () => import('./contrat/contrat.module').then(m => m.ContratModule),
      },
      {
        path: 'adhesion',
        data: { pageTitle: 'payrollApp.adhesion.home.title' },
        loadChildren: () => import('./adhesion/adhesion.module').then(m => m.AdhesionModule),
      },
      {
        path: 'entity-adhesion',
        data: { pageTitle: 'payrollApp.entityAdhesion.home.title' },
        loadChildren: () => import('./entity-adhesion/entity-adhesion.module').then(m => m.EntityAdhesionModule),
      },
      {
        path: 'affectation',
        data: { pageTitle: 'payrollApp.affectation.home.title' },
        loadChildren: () => import('./affectation/affectation.module').then(m => m.AffectationModule),
      },
      {
        path: 'pays',
        data: { pageTitle: 'payrollApp.pays.home.title' },
        loadChildren: () => import('./pays/pays.module').then(m => m.PaysModule),
      },
      {
        path: 'plate',
        data: { pageTitle: 'payrollApp.plate.home.title' },
        loadChildren: () => import('./plate/plate.module').then(m => m.PlateModule),
      },
      {
        path: 'famille',
        data: { pageTitle: 'payrollApp.famille.home.title' },
        loadChildren: () => import('./famille/famille.module').then(m => m.FamilleModule),
      },
      {
        path: 'rebrique',
        data: { pageTitle: 'payrollApp.rebrique.home.title' },
        loadChildren: () => import('./rebrique/rebrique.module').then(m => m.RebriqueModule),
      },
      {
        path: 'solde-absence-paie',
        data: { pageTitle: 'payrollApp.soldeAbsencePaie.home.title' },
        loadChildren: () => import('./solde-absence-paie/solde-absence-paie.module').then(m => m.SoldeAbsencePaieModule),
      },
      {
        path: 'payroll-info',
        data: { pageTitle: 'payrollApp.payrollInfo.home.title' },
        loadChildren: () => import('./payroll-info/payroll-info.module').then(m => m.PayrollInfoModule),
      },
      {
        path: 'management-resource',
        data: { pageTitle: 'payrollApp.managementResource.home.title' },
        loadChildren: () => import('./management-resource/management-resource.module').then(m => m.ManagementResourceModule),
      },
      {
        path: 'nature-config',
        data: { pageTitle: 'payrollApp.natureConfig.home.title' },
        loadChildren: () => import('./nature-config/nature-config.module').then(m => m.NatureConfigModule),
      },
      {
        path: 'management-resource-fun',
        data: { pageTitle: 'payrollApp.managementResourceFun.home.title' },
        loadChildren: () => import('./management-resource-fun/management-resource-fun.module').then(m => m.ManagementResourceFunModule),
      },
      {
        path: 'payroll',
        data: { pageTitle: 'payrollApp.payroll.home.title' },
        loadChildren: () => import('./payroll/payroll.module').then(m => m.PayrollModule),
      },
      {
        path: 'nature-mvt-paie',
        data: { pageTitle: 'payrollApp.natureMvtPaie.home.title' },
        loadChildren: () => import('./nature-mvt-paie/nature-mvt-paie.module').then(m => m.NatureMvtPaieModule),
      },
      {
        path: 'nature-eligibilite',
        data: { pageTitle: 'payrollApp.natureEligibilite.home.title' },
        loadChildren: () => import('./nature-eligibilite/nature-eligibilite.module').then(m => m.NatureEligibiliteModule),
      },
      {
        path: 'target-eligible',
        data: { pageTitle: 'payrollApp.targetEligible.home.title' },
        loadChildren: () => import('./target-eligible/target-eligible.module').then(m => m.TargetEligibleModule),
      },
      {
        path: 'eligibilite',
        data: { pageTitle: 'payrollApp.eligibilite.home.title' },
        loadChildren: () => import('./eligibilite/eligibilite.module').then(m => m.EligibiliteModule),
      },
      {
        path: 'eligibilite-exclude',
        data: { pageTitle: 'payrollApp.eligibiliteExclude.home.title' },
        loadChildren: () => import('./eligibilite-exclude/eligibilite-exclude.module').then(m => m.EligibiliteExcludeModule),
      },
      {
        path: 'mouvement-paie',
        data: { pageTitle: 'payrollApp.mouvementPaie.home.title' },
        loadChildren: () => import('./mouvement-paie/mouvement-paie.module').then(m => m.MouvementPaieModule),
      },
      {
        path: 'demande-calcul-paie',
        data: { pageTitle: 'payrollApp.demandeCalculPaie.home.title' },
        loadChildren: () => import('./demande-calcul-paie/demande-calcul-paie.module').then(m => m.DemandeCalculPaieModule),
      },
      {
        path: 'plate-info',
        data: { pageTitle: 'payrollApp.plateInfo.home.title' },
        loadChildren: () => import('./plate-info/plate-info.module').then(m => m.PlateInfoModule),
      },
      {
        path: 'user-log',
        data: { pageTitle: 'payrollApp.userLog.home.title' },
        loadChildren: () => import('./user-log/user-log.module').then(m => m.UserLogModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
