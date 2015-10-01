package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import evaluator.HITEvaluator;
import featureprocessing.ThroughputExtractor;

public class GenerateThroughputTrainingData {

	private static String header1 = "#### training ####";
	private static String headerGeneral = "timestamp,groupID,marketTimestamp,throughput,";
	private static String headerMarket = "hitGroupsArrived,hitGroupsCompleted,hitsAvailableUI,hitsCompleted,rewardsCompleted,rewardsArrived,hitsArrived,hitGroupsAvailableUI,";
	private static String headerTask = "reward,timeAllotted,location,master,totalApproved,approvalRate,titleLength,descLength,"
			+ "amountKeywords,linkCount,wordCount,imageCount,bodyTextPct,emphTextPct,cssCount,scriptCount,"
			+ "inputCount,textGroupCount,imageAreaCount,visualAreaCount,w3cPct,hueAvg,satAvg,valAvg,colorfulness1,colorfulness2,initialHits";
	private static String headerSemantic = ",keyword_above,keyword_activity,keyword_additional,keyword_adult,keyword_agreement,keyword_alegion,keyword_an,keyword_analysis,keyword_and,keyword_announcement,keyword_app,keyword_application,keyword_approve,keyword_assistance,keyword_assurance,keyword_attribute,keyword_audio,keyword_basic,keyword_benefit,keyword_bonus,keyword_boxes,keyword_breast,keyword_browsing,keyword_bte,keyword_build,keyword_bunny,keyword_calendar,keyword_calls,keyword_calories,keyword_cancer,keyword_caption,keyword_car,keyword_carnegie,keyword_cartoon,keyword_castingwords,keyword_categories,keyword_categorization,keyword_cause,keyword_cgv,keyword_changing,keyword_child,keyword_china,keyword_chinese,keyword_christian,keyword_classification,keyword_clean,keyword_cleaner,keyword_click,keyword_clip,keyword_clipart,keyword_cognitive,keyword_collection,keyword_college,keyword_column,keyword_communities,keyword_community,keyword_company,keyword_compare,keyword_conditions,keyword_conference,keyword_content,keyword_control,keyword_copy,keyword_copyediting,keyword_copywriting,keyword_correction,keyword_count,keyword_counting,keyword_course,keyword_create,keyword_creation,keyword_crisis,keyword_crowd,keyword_currency,keyword_cw,keyword_data,keyword_deal,keyword_demographics,keyword_difference,keyword_different,keyword_disaster,keyword_discussion,keyword_doctor,keyword_documents,keyword_donate,keyword_drawing,keyword_drink,keyword_each,keyword_easy,keyword_echo,keyword_edgar,keyword_editing,keyword_education,keyword_emergency,keyword_english,keyword_entry,keyword_error,keyword_etf,keyword_events,keyword_excel,keyword_exchange,keyword_experiment,keyword_extraction,keyword_face,keyword_faces,keyword_fashion,keyword_fast,keyword_feature,keyword_feedback,keyword_fiction,keyword_file,keyword_film,keyword_films,keyword_filmsupply,keyword_filters,keyword_finance,keyword_financial,keyword_find,keyword_fitness,keyword_flammeum,keyword_food,keyword_for,keyword_foreign,keyword_forum,keyword_fun,keyword_fund,keyword_funny,keyword_fx,keyword_german,keyword_germany,keyword_giving,keyword_good,keyword_google,keyword_grammatical,keyword_gratitude,keyword_greeting,keyword_handwriting,keyword_heads,keyword_health,keyword_healthcare,keyword_high,keyword_hit,keyword_hope,keyword_humanitarian,keyword_humor,keyword_illustration,keyword_image,keyword_images,keyword_inc,keyword_information,keyword_internet,keyword_investor,keyword_is,keyword_keynote,keyword_kids,keyword_language,keyword_life,keyword_line,keyword_linguistics,keyword_links,keyword_listed,keyword_mac,keyword_malaria,keyword_media,keyword_mellon,keyword_message,keyword_micro,keyword_microloan,keyword_minute,keyword_moderation,keyword_monthly,keyword_mp3,keyword_name,keyword_natural,keyword_needs,keyword_networking,keyword_news,keyword_no,keyword_nonprofit,keyword_null,keyword_nutrition,keyword_ocmp,keyword_ocmp14,keyword_ocr,keyword_of,keyword_official,keyword_on,keyword_one,keyword_opinion,keyword_or,keyword_orders,keyword_organization,keyword_outbound,keyword_pages,keyword_paul,keyword_payment,keyword_phone,keyword_photo,keyword_photos,keyword_photoshop,keyword_pic,keyword_pics,keyword_picture,keyword_pizza,keyword_plane,keyword_please,keyword_politeness,keyword_poverty,keyword_product,keyword_proofreading,keyword_proper,keyword_prospectus,keyword_psychology,keyword_pullen,keyword_qa,keyword_quality,keyword_question,keyword_quick,keyword_rate,keyword_rating,keyword_reading,keyword_real,keyword_receipt,keyword_recognition,keyword_record,keyword_refinement,keyword_rehabilitation,keyword_reject,keyword_relations,keyword_research,keyword_retail,keyword_review,keyword_reviews,keyword_revision,keyword_rewriting,keyword_run2,keyword_safe,keyword_sai,keyword_sample,keyword_scenario,keyword_scene,keyword_school,keyword_science,keyword_scraping,keyword_search,keyword_sec,keyword_sentence,keyword_shanghai,keyword_shares,keyword_shoes,keyword_shopping,keyword_short,keyword_simple,keyword_site,keyword_social,keyword_speak,keyword_speaker,keyword_speech,keyword_speechink,keyword_speed,keyword_sponsorship,keyword_standards,keyword_statement,keyword_stock,keyword_stocks,keyword_story,keyword_study,keyword_summary,keyword_supermarket,keyword_supply,keyword_survey,keyword_tag,keyword_tagging,keyword_tc,keyword_telephone,keyword_terms,keyword_testing,keyword_text,keyword_the,keyword_thread,keyword_traded,keyword_transcribe,keyword_transcription,keyword_transfer,keyword_translation,keyword_truck,keyword_two,keyword_undefined,keyword_university,keyword_updated,keyword_upload,keyword_url,keyword_usability,keyword_ux,keyword_velatio,keyword_video,keyword_videos,keyword_vision,keyword_voicemail,keyword_water,keyword_web,keyword_website,keyword_wikipedia,keyword_with,keyword_workforce,keyword_world,keyword_worldvision,keyword_worldwide,keyword_write,keyword_writing,keyword_wv,keyword_wvi,keyword_ycharts,keyword_yes,keyword_zipinion,unigram_00,unigram_00__n__,unigram_01,unigram_04,unigram_08,unigram_10,unigram_11,unigram_12,unigram_13,unigram_14,unigram_15,unigram_16,unigram_17,unigram_18,unigram_19,unigram_1__n__,unigram_20,unigram_200,unigram_2012,unigram_2014,unigram_2015,unigram_21,unigram_22,unigram_23,unigram_24,unigram_25,unigram_26,unigram_27,unigram_28,unigram_29,unigram_2__n__,unigram_30,unigram_32,unigram_33,unigram_34,unigram_35,unigram_36,unigram_3__n__,unigram_40,unigram_44,unigram_45,unigram_47,unigram_50,unigram_54,unigram_55,unigram_64,unigram_65,unigram___n____n__,unigram_abbreviation,unigram_able,unigram_abuse__n__,unigram_accept,unigram_acceptable,unigram_accepted,unigram_accepting,unigram_access,unigram_according,unigram_accordingly,unigram_account,unigram_accuracy,unigram_accurate,unigram_accurately,unigram_activate,unigram_activity,unigram_actual,unigram_actually,unigram_add,unigram_added,unigram_addition,unigram_additional,unigram_additionally,unigram_address,unigram_addresses,unigram_afraid,unigram_age,unigram_agree,unigram_alcohol,unigram_allow,unigram_allows,unigram_amazon,unigram_animals,unigram_annotate,unigram_answer,unigram_answered,unigram_answers,unigram_anytime,unigram_app,unigram_appear,unigram_appears,unigram_apple,unigram_application,unigram_apply,unigram_appointment,unigram_appropriate,unigram_approve,unigram_approved,unigram_areas,unigram_art,unigram_article,unigram_articles,unigram_ask,unigram_asked,unigram_assess,unigram_assignment,unigram_assurance,unigram_attached,unigram_attention,unigram_audio,unigram_author,unigram_availability,unigram_available,unigram_average,unigram_avoid,unigram_baby,unigram_background,unigram_bad,unigram_based,unigram_beauty,unigram_begin,unigram_beginning,unigram_behalf,unigram_beneath,unigram_benefit,unigram_benefits,unigram_best,unigram_better,unigram_big,unigram_bit,unigram_blank,unigram_block,unigram_blocked,unigram_blog,unigram_blue,unigram_blurry,unigram_body,unigram_bonus,unigram_bonuses,unigram_book,unigram_books,unigram_box,unigram_boxes,unigram_boy,unigram_brand,unigram_brief,unigram_broken,unigram_brown,unigram_browse,unigram_browser,unigram_build,unigram_building,unigram_business,unigram_businesses,unigram_button,unigram_buy,unigram_caca,unigram_called,unigram_calling,unigram_calls,unigram_camera,unigram_campaign,unigram_cancel,unigram_capital,unigram_care,unigram_careful,unigram_carefully,unigram_cart,unigram_case,unigram_cases,unigram_cat,unigram_categories,unigram_category,unigram_category__n__,unigram_causes,unigram_certain,unigram_chances,unigram_change,unigram_changed,unigram_changes,unigram_changing,unigram_charge,unigram_check,unigram_checkbox,unigram_child,unigram_children,unigram_china,unigram_chinese,unigram_choice,unigram_choose,unigram_christian,unigram_chrome,unigram_city,unigram_class,unigram_classroom,unigram_clean,unigram_clear,unigram_click,unigram_clicking,unigram_clip,unigram_close,unigram_closed,unigram_closest,unigram_clothing,unigram_code,unigram_color,unigram_colors,unigram_column,unigram_columns,unigram_com,unigram_com__n__,unigram_come,unigram_comes,unigram_comment,unigram_comments,unigram_common,unigram_communities,unigram_community,unigram_companies,unigram_company,unigram_compare,unigram_comparison,unigram_complete,unigram_completed,unigram_computer,unigram_conditions,unigram_confidence,unigram_confident,unigram_confirm,unigram_congratulations,unigram_connection,unigram_consider,unigram_considered,unigram_consistently,unigram_constructed,unigram_contact,unigram_contain,unigram_contains,unigram_content,unigram_content__n__,unigram_context,unigram_continue,unigram_continuing,unigram_control,unigram_controls,unigram_copy,unigram_corporation,unigram_correct,unigram_correction,unigram_correctly,unigram_correspondence,unigram_cost,unigram_count,unigram_country,unigram_county,unigram_couple,unigram_coupon,unigram_covered,unigram_covers,unigram_create,unigram_credit,unigram_criteria,unigram_crowd,unigram_crying,unigram_current,unigram_currently,unigram_customer,unigram_customers,unigram_dark,unigram_data,unigram_database,unigram_date,unigram_days,unigram_dedicated,unigram_deep,unigram_defined,unigram_degree,unigram_delete,unigram_deliver,unigram_demographic,unigram_depending,unigram_described,unigram_describes,unigram_describing,unigram_description,unigram_design,unigram_detailed,unigram_details,unigram_determine,unigram_determined,unigram_development,unigram_device,unigram_did,unigram_didn,unigram_difference,unigram_differences,unigram_different,unigram_difficult,unigram_direct,unigram_directly,unigram_director,unigram_directory,unigram_discussion,unigram_display,unigram_disregard,unigram_doctor,unigram_doctors,unigram_document,unigram_documents,unigram_does,unigram_doesn,unigram_dog,unigram_doing,unigram_don,unigram_double,unigram_download,unigram_downloading,unigram_dp,unigram_drag,unigram_drivers,unigram_drug,unigram_duplicate,unigram_early,unigram_easier,unigram_eastern,unigram_easy,unigram_edge,unigram_edit,unigram_editing,unigram_education,unigram_effect,unigram_effective,unigram_elderly,unigram_element,unigram_email,unigram_employer,unigram_en,unigram_end,unigram_engineering,unigram_english,unigram_ensure,unigram_enter,unigram_entire,unigram_entries,unigram_entry,unigram_environment,unigram_equally,unigram_equivalent,unigram_event,unigram_ex,unigram_exact,unigram_exactly,unigram_example,unigram_examples,unigram_excel,unigram_existing,unigram_exists,unigram_expand,unigram_expect,unigram_experience,unigram_experiencing,unigram_explain,unigram_explicitly,unigram_explorer,unigram_extension,unigram_extent,unigram_external,unigram_extract,unigram_extremely,unigram_eye,unigram_eyes,unigram_face,unigram_false,unigram_families,unigram_family,unigram_faq__n__,unigram_far,unigram_fashion,unigram_fast,unigram_features,unigram_featuring,unigram_federal,unigram_feedback,unigram_feel,unigram_female,unigram_field,unigram_fighting,unigram_file,unigram_files,unigram_finally,unigram_financial,unigram_find__n__,unigram_fine,unigram_finger,unigram_finish,unigram_finished,unigram_firefox,unigram_fit,unigram_flag,unigram_focus,unigram_follow,unigram_following,unigram_follows,unigram_food,unigram_form,unigram_format,unigram_formatted,unigram_forms,unigram_forums,unigram_frame,unigram_free,unigram_frequently,unigram_friends,unigram_fully,unigram_fun,unigram_function,unigram_funds,unigram_future,unigram_game,unigram_gender,unigram_general,unigram_geographical,unigram_gestures,unigram_getting,unigram_given,unigram_goal,unigram_going,unigram_good,unigram_google,unigram_got,unigram_government,unigram_graded,unigram_grammar,unigram_grant,unigram_graphs,unigram_great,unigram_greater,unigram_group,unigram_guardian,unigram_guidance,unigram_guide,unigram_guidelines,unigram_hair,unigram_hand,unigram_hands,unigram_happen,unigram_happy,unigram_hat,unigram_hate,unigram_haven,unigram_head,unigram_heads,unigram_health,unigram_healthcare,unigram_help,unigram_helpful,unigram_helping,unigram_hide,unigram_high,unigram_highest,unigram_highlighted,unigram_highly,unigram_hint,unigram_hints,unigram_hit,unigram_hits,unigram_hl,unigram_hold,unigram_home,unigram_homepage,unigram_hours,unigram_htm,unigram_html,unigram_http,unigram_https,unigram_human,unigram_humanitarian,unigram_icon,unigram_id,unigram_identified,unigram_identify,unigram_identifying,unigram_identity,unigram_ignore,unigram_illegal,unigram_image,unigram_image__n__,unigram_images,unigram_immediately,unigram_important,unigram_impossible,unigram_improve,unigram_inappropriate,unigram_include,unigram_includes,unigram_including,unigram_income,unigram_incorrect,unigram_increase,unigram_independent,unigram_indicate,unigram_indicated,unigram_indicates,unigram_individual,unigram_indoor,unigram_industry,unigram_info,unigram_info__n__,unigram_information,unigram_informative,unigram_injustice,unigram_input,unigram_instead,unigram_instruction,unigram_instructions,unigram_insurance,unigram_interact,unigram_interested,unigram_interesting,unigram_interface,unigram_international,unigram_internet,unigram_interview,unigram_investments,unigram_investor,unigram_ip,unigram_iphone,unigram_issue,unigram_issues,unigram_item,unigram_items,unigram_itunes,unigram_javascript,unigram_job,unigram_jobs,unigram_john,unigram_joint,unigram_judgment,unigram_july,unigram_just,unigram_keeping,unigram_key,unigram_keyboard,unigram_kids,unigram_kind,unigram_kinks,unigram_know,unigram_known,unigram_label,unigram_labeled,unigram_labeling,unigram_labor,unigram_landscape,unigram_language,unigram_large,unigram_later,unigram_law,unigram_learn,unigram_leave,unigram_left,unigram_legal,unigram_legitimate,unigram_let,unigram_letter,unigram_level,unigram_light,unigram_like,unigram_likely,unigram_line,unigram_link,unigram_linkedin,unigram_links,unigram_list,unigram_listed,unigram_listen,unigram_listing,unigram_little,unigram_ll,unigram_load,unigram_loading,unigram_locate,unigram_location,unigram_log,unigram_logo,unigram_long,unigram_longer,unigram_look,unigram_looking,unigram_looks,unigram_lost,unigram_lot,unigram_love,unigram_lower,unigram_machine,unigram_main,unigram_maintain,unigram_make,unigram_makes,unigram_making,unigram_male,unigram_man,unigram_manually,unigram_manufacturer,unigram_mark,unigram_marked,unigram_master,unigram_match,unigram_matches,unigram_material,unigram_materials,unigram_maximum,unigram_meaning,unigram_means,unigram_mechanical,unigram_media,unigram_medium,unigram_meet,unigram_member,unigram_men,unigram_message,unigram_messages,unigram_microsoft,unigram_middle,unigram_mind,unigram_minimum,unigram_minute,unigram_minutes,unigram_missing,unigram_mistakes,unigram_mistaking,unigram_mode,unigram_model,unigram_money,unigram_ms,unigram_msrp,unigram_mt,unigram_mturk,unigram_multiple,unigram_murder__n__,unigram_music,unigram_names,unigram_narrow,unigram_national,unigram_native,unigram_nature,unigram_navigate,unigram_navy,unigram_necessary,unigram_need,unigram_needed,unigram_needs,unigram_net,unigram_network,unigram_new,unigram_newest,unigram_news,unigram_nice,unigram_non,unigram_normal,unigram_note,unigram_notes,unigram_notice,unigram_number,unigram_numbers,unigram_ny,unigram_object,unigram_objects,unigram_offer,unigram_offers,unigram_office,unigram_official,unigram_ok,unigram_ok__n__,unigram_old,unigram_online,unigram_open,unigram_operation,unigram_opportunity,unigram_opposite,unigram_option,unigram_options,unigram_order,unigram_org,unigram_organization,unigram_original,unigram_overall,unigram_page,unigram_page__n__,unigram_pages,unigram_paid,unigram_paragraph,unigram_participate,unigram_participation,unigram_parts,unigram_party,unigram_past,unigram_paste,unigram_patient,unigram_patients,unigram_pause,unigram_pay,unigram_payment,unigram_people,unigram_perform,unigram_performance,unigram_performing,unigram_period,unigram_permission,unigram_person,unigram_personal,unigram_personally,unigram_personnel,unigram_persons,unigram_phone,unigram_photo,unigram_photos,unigram_phrase,unigram_phrases,unigram_physical,unigram_picture,unigram_piece,unigram_place,unigram_plain,unigram_plan,unigram_platform,unigram_play,unigram_player,unigram_plus,unigram_point,unigram_pop,unigram_populate,unigram_populated,unigram_position,unigram_positive,unigram_possible,unigram_possibly,unigram_post,unigram_posted,unigram_posting,unigram_potential,unigram_poverty,unigram_practice,unigram_pre,unigram_prepare,unigram_present,unigram_presented,unigram_presenting,unigram_pretty,unigram_preview,unigram_previous,unigram_previously,unigram_price,unigram_prices,unigram_primary,unigram_printed,unigram_prison,unigram_privacy,unigram_private,unigram_probably,unigram_problem,unigram_problems,unigram_process,unigram_product,unigram_products,unigram_professional,unigram_profile,unigram_program,unigram_progress,unigram_project,unigram_promotion,unigram_proper,unigram_protection,unigram_provide,unigram_provided,unigram_provider,unigram_provides,unigram_providing,unigram_public,unigram_publish,unigram_published,unigram_publishes,unigram_punctuation,unigram_purchase,unigram_purpose,unigram_qualification,unigram_qualifications,unigram_qualify,unigram_qualifying,unigram_quality,unigram_quantity,unigram_question,unigram_questions,unigram_questions__n__,unigram_quickly,unigram_quite,unigram_raindrops,unigram_range,unigram_ranked,unigram_ranking,unigram_rare,unigram_rate,unigram_rating,unigram_ratings,unigram_reach,unigram_read,unigram_real,unigram_really,unigram_reason,unigram_receive,unigram_received,unigram_receives,unigram_recent,unigram_recently,unigram_recommend,unigram_recommended,unigram_record,unigram_recorded,unigram_recording,unigram_recordings,unigram_rectangle,unigram_red,unigram_reference,unigram_referencing,unigram_reflect,unigram_reflects,unigram_refresh,unigram_regarding,unigram_region,unigram_registering,unigram_registration,unigram_regular,unigram_reject,unigram_rejected,unigram_rejection,unigram_related,unigram_relations,unigram_relevant,unigram_reload,unigram_remember,unigram_remove,unigram_removed,unigram_repeat,unigram_replace,unigram_replacement,unigram_report,unigram_reports,unigram_represent,unigram_request,unigram_requester,unigram_requests,unigram_require,unigram_required,unigram_requirements,unigram_requires,unigram_research,unigram_resize,unigram_resolution,unigram_resources,unigram_respond,unigram_response,unigram_responses,unigram_rest,unigram_result,unigram_results,unigram_retail,unigram_retrieve,unigram_return,unigram_returned,unigram_revealing,unigram_review,unigram_reviews,unigram_reward,unigram_right,unigram_rights,unigram_round,unigram_rule,unigram_rules,unigram_run,unigram_sad,unigram_safety,unigram_said,unigram_salary,unigram_sale,unigram_sample,unigram_satisfied,unigram_save,unigram_saved,unigram_say,unigram_sc,unigram_scale,unigram_scenario,unigram_scenarios,unigram_school,unigram_screens,unigram_screenshot,unigram_screenshots,unigram_scroll,unigram_search,unigram_searched,unigram_searching,unigram_second,unigram_secondary,unigram_seconds,unigram_section,unigram_security,unigram_select,unigram_selection,unigram_selling,unigram_send,unigram_sense,unigram_sentence,unigram_sentences,unigram_separate,unigram_separately,unigram_series,unigram_server,unigram_service,unigram_services,unigram_sexual,unigram_shanghai,unigram_share,unigram_shipping,unigram_shirt,unigram_shopping,unigram_short,unigram_shorter,unigram_shot,unigram_showing,unigram_shown,unigram_shows,unigram_sign,unigram_similar,unigram_simple,unigram_simply,unigram_single,unigram_site,unigram_sites,unigram_situation,unigram_size,unigram_skip,unigram_sku,unigram_slightly,unigram_slow,unigram_small,unigram_smaller,unigram_social,unigram_software,unigram_somewhat,unigram_soon,unigram_sound,unigram_sounding,unigram_sounds,unigram_source,unigram_speak,unigram_speaker,unigram_speakers,unigram_speaking,unigram_special,unigram_specific,unigram_speech,unigram_speed,unigram_spelling,unigram_spending,unigram_staff,unigram_standards,unigram_standing,unigram_star,unigram_start,unigram_starting,unigram_starts,unigram_state,unigram_stated,unigram_statement,unigram_states,unigram_step,unigram_steps,unigram_steps__n__,unigram_store,unigram_stories,unigram_story,unigram_study,unigram_style,unigram_subject,unigram_submission,unigram_submit,unigram_submit__n__,unigram_submitted,unigram_submitting,unigram_subtle,unigram_suggested,unigram_suggestion,unigram_suggestions,unigram_summary,unigram_support,unigram_sur,unigram_sure,unigram_surgeon,unigram_survey,unigram_tab,unigram_tackling,unigram_tag,unigram_taken,unigram_target,unigram_task,unigram_tasks,unigram_technical,unigram_techniques,unigram_technology,unigram_telephone,unigram_television,unigram_tell,unigram_term,unigram_terms,unigram_test,unigram_testing,unigram_tests,unigram_text,unigram_text__n__,unigram_thank,unigram_thanks,unigram_think,unigram_thoughts,unigram_thread,unigram_time,unigram_times,unigram_tips,unigram_title,unigram_topic,unigram_total,unigram_trafficking__n__,unigram_training,unigram_transcribe,unigram_transcript,unigram_transcription,unigram_transfer,unigram_translate,unigram_transmit,unigram_true,unigram_try,unigram_trying,unigram_turk,unigram_turkers,unigram_turn,unigram_tweet,unigram_type,unigram_types,unigram_typically,unigram_um,unigram_unable,unigram_understand,unigram_understanding,unigram_unfortunately,unigram_unimportant,unigram_unique,unigram_unit,unigram_united,unigram_universal,unigram_unless,unigram_update,unigram_updated,unigram_updates,unigram_upload,unigram_url,unigram_urls,unigram_use,unigram_used,unigram_useful,unigram_user,unigram_using,unigram_usually,unigram_valid,unigram_validate,unigram_value,unigram_ve,unigram_verbatim,unigram_verified,unigram_verify,unigram_version,unigram_versions,unigram_versus,unigram_video,unigram_videos,unigram_view,unigram_violence,unigram_visible,unigram_vision,unigram_visit,unigram_voice,unigram_volume,unigram_waist,unigram_wait,unigram_want,unigram_warning,unigram_watch,unigram_water,unigram_way,unigram_ways,unigram_weapons,unigram_web,unigram_webpage,unigram_website,unigram_websites,unigram_week,unigram_welcome,unigram_white,unigram_wide,unigram_window,unigram_woman,unigram_women,unigram_won,unigram_word,unigram_words,unigram_work,unigram_worker,unigram_workers,unigram_working,unigram_works,unigram_world,unigram_worldwide,unigram_worry,unigram_write,unigram_writing,unigram_written,unigram_wrong,unigram_www,unigram_year,unigram_years,unigram_yelp,unigram_yes,unigram_zip,unigram_zoom,topic_0,topic_1,topic_2,topic_3,topic_4";
	private static String header3 = "#### test ####";

	public static void main(String[] args) {

		// 1. Get timestamps for all 5-hour blocks
		ArrayList<String> blocks = getTimestampBlocks();

		// 2. Keep track of training and test data
		ArrayList<String> trainingLines = new ArrayList<String>();
		ArrayList<String> testLines = new ArrayList<String>();

		trainingLines.add(headerGeneral + headerMarket + headerTask
				+ headerSemantic);
		testLines.add(headerGeneral + headerMarket + headerTask
				+ headerSemantic);

		// 3. For every timestamp past the first four, get the data
		String[] currentBlock;
		for (String block : blocks) {

			currentBlock = block.split(";");

			ArrayList<String> hitsAtTime;
			for (int i = 0; i < 5; i++) {
				// Data = general information, market information, HIT info

				// Get HITs for the timestamp under evaluation.
				hitsAtTime = getHITsAtTime(currentBlock[i]);

				// Add market data for this hour to the basic line.
				String timestampLine = getMarketDataStringAtTime(currentBlock[i]);

				// Construct result lines for every HIT in this timestamp
				String resultLine = "";
				String groupID;
				for (String hit : hitsAtTime) {
					resultLine += currentBlock[4] + "," + hit;

					groupID = hit.split(",")[0];

					String hitString = getHITstring(groupID);
					if (!hitString.isEmpty()) {
						resultLine += "" + timestampLine + "," + hitString;

						// Get semantic features
						String semanticString = getSemanticString(groupID);

						if (!semanticString.isEmpty()) {
							resultLine += "," + semanticString;
							if (i == 4) {
								testLines.add(resultLine);
							} else {
								trainingLines.add(resultLine);
							}
							// }
							resultLine = "";
						}
					}

				}
			}
		}

		// 4. Output the results to file
		HITEvaluator.outputToCsv(trainingLines, "training.csv");
		HITEvaluator.outputToCsv(testLines, "test.csv");
		System.out.println("Done.");
	}

	public static void outputSeparateFiles() {

		// 1. Get timestamps for all 5-hour blocks
		ArrayList<String> blocks = getTimestampBlocks();

		ArrayList<Long> timestamps = ThroughputExtractor.readTimeStamps();

		// 2. For every timestamp past the first four, get the data
		String[] currentBlock;
		for (String block : blocks) {
			ArrayList<String> output = new ArrayList<String>();
			output.add(header1);
			output.add(headerGeneral + headerMarket + headerTask);

			currentBlock = block.split(";");

			ArrayList<String> hitsAtTime;
			for (int i = 0; i < 5; i++) {
				if (i == 4) {
					output.add(header3);
				}

				// Data = general information, market information, HIT info

				// Get HITs for the timestamp under evaluation.
				hitsAtTime = getHITsAtTime(currentBlock[i]);

				// Add market data for this hour to the basic line.
				String timestampLine = getMarketDataStringAtTime(currentBlock[i]);

				// Construct result lines for every HIT in this timestamp
				String resultLine = "";
				for (String hit : hitsAtTime) {
					resultLine += currentBlock[4] + hit;

					String hitString = getHITstring(hit.split(",")[0]);
					if (!hitString.isEmpty()) {
						resultLine += timestampLine + hitString;
						output.add(resultLine);
					}
				}

			}

			// 4. Output the results to file
			// Only include output with test data
			String last = output.get(output.size() - 1);
			if (!last.equals(header3)) {
				// HITEvaluator.outputToCsv(output, currentBlock[4] + ".csv");
				System.out.println("Wrote " + currentBlock[4] + ".csv.");
			}
		}

	}

	// Divide given timestamps using sliding window of 5 hours
	private static ArrayList<String> getTimestampBlocks() {
		// Get all timestamps
		ArrayList<Long> timestamps = ThroughputExtractor.readTimeStamps();

		// Split up into blocks of 5: 4 training, 1 test
		ArrayList<String> blocks = new ArrayList<String>();

		int i = 0;
		String current = "";
		for (Long l : timestamps) {
			i++;
			current += l.toString();
			if (i != 5) {
				current += ";";
			} else if (i == 5) {
				// System.out.println(current);
				blocks.add(current);
				current = current.substring(current.indexOf(";") + 1) + ";";
				i = 4;
			}
		}

		return blocks;
	}

	// Returns HITs at timestamp in format (groupID,timestamp,throughput)
	private static ArrayList<String> getHITsAtTime(String timestamp) {

		ArrayList<String> result = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\throughput_data.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				if (nextLine[1].contains(timestamp)) {
					result.add(line.replaceAll("\"", ""));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getMarketDataStringAtTime(String timestamp) {
		String result = "";

		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\timestamps_sorted.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				if (nextLine[0].contains(timestamp)) {
					result = line.substring(line.indexOf(",")).replaceAll("\"",
							"");
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getHITstring(String groupID) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\attributes_only_reinstantiated.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				// exclude 0 (groupid), 21 (textarea),
				// 22 (nontextarea), 30 (old throughput)
				if (nextLine[0].equals(groupID)) {
					for (int i = 1; i < nextLine.length; i++) {
						result += nextLine[i] + ",";

					}
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.isEmpty()) {
			return "";
		} else
			// System.out.println(result);
			return result.substring(0, result.length() - 1);
	}

	private static String getSemanticString(String groupID) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(HITEvaluator.baseDir
					+ "data\\keyword_unigram_topic_features.csv");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line = "";
			String[] nextLine;

			// Read each line in turn
			while ((line = reader.readLine()) != null) {
				nextLine = line.split(",");
				// Get the line for this groupID
				if (nextLine[0].equals(groupID)) {
					for (int i = 1; i < nextLine.length; i++) {
						result += nextLine[i] + ",";
					}

					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.isEmpty()) {
			return "";
		} else
			return result.substring(0, result.length() - 1);
	}

}
