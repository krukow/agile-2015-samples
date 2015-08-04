Given(/^I'm on the main page$/) do
  wait_for_view id:'btnplay'
  sleep 1
end

And(/^I start playing$/) do
  tap id:'btnplay'
  sleep 1
end

Then(/^an image of a super hero is shown$/) do
  wait_for_view id:'imageView'

end

When(/^I choose a letter$/) do
  tap id:'button'
  sleep 1
end

Then(/^that letter becomes selected$/) do
  letter = query("* id:'gui_answer1' button", :text).first
  raise "Expected a selected letter" unless letter
end